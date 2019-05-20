package com.zzlbe.core.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzlbe.core.business.ActivityBusiness;
import com.zzlbe.core.business.OrderBusiness;
import com.zzlbe.core.common.ErrorCodeEnum;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.constant.OrderStatusEnum;
import com.zzlbe.core.request.*;
import com.zzlbe.core.response.CustomerVO;
import com.zzlbe.core.response.OrderVO;
import com.zzlbe.dao.entity.*;
import com.zzlbe.dao.mapper.*;
import com.zzlbe.dao.search.AmountSearch;
import com.zzlbe.dao.search.CustomerSearch;
import com.zzlbe.dao.search.GoodsSearch;
import com.zzlbe.dao.search.OrderSearch;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * PROJECT: sales management
 * DESCRIPTION: 订单相关业务实现
 *
 * @author amos
 * @date 2019/4/20
 */
@Component("orderBusiness")
public class OrderBusinessImpl extends BaseBusinessImpl implements OrderBusiness {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private SellerMapper sellerMapper;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private AreaMapper areaMapper;
    @Resource
    private ActivityBusiness activityBusiness;

    /**
     * 订单审核状态
     * 1：审核通过
     * 0：审核拒绝
     */
    private static final Integer ORDER_AUDIT_SUCCESS = 1;
    private static final Integer ORDER_AUDIT_FAIL = 2;
    /**
     * 订单申请售后原因：1退货 || 2退款
     */
    private static final Integer ORDER_SALE_RETURN = 1;
    private static final Integer ORDER_SALE_REFUND = 2;

    @Override
    public GenericResponse preview(OrderForm orderForm) {
        // 校验用户
        UserEntity userEntity = getUserEntity(orderForm);
        if (userEntity == null) {
            return new GenericResponse<>(ErrorCodeEnum.USER_NOT_FOUND);
        }
        // 校验商品(存在|下架)
        GenericResponse checkGoodsResponse = checkGoods(orderForm.getOrGoodsId());
        if (!checkGoodsResponse.successful()) {
            return checkGoodsResponse;
        }
        GoodsEntity goodsEntity = (GoodsEntity) checkGoodsResponse.getBody();

        // 基础信息设置
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderForm, orderEntity);
        orderEntity.setOrUserId(userEntity.getId());
        orderEntity.setOrDatetime(new Date());

        // 安全起见：重新设置金额(单价 * 数量)
        BigDecimal goodsCount = new BigDecimal(orderForm.getOrCount());
        orderEntity.setOrPrice(goodsEntity.getPrice());
        orderEntity.setOrTotalAmount(goodsEntity.getPrice().multiply(goodsCount));

        // 下单审核状态：0待审核，1审核通过，2审核拒绝（销售员下单默认不需要审核）
        orderEntity.setOrType(orderForm.getOrderFlag() ? 1 : 0);

        // 指定订单销售员
        Long orderSellerId = orderForm.getOrSellerId();
        if (orderForm.getOrderFlag()) {
            // 销售员下单销售员ID不能为空
            if (orderSellerId == null) {
                return new GenericResponse(ErrorCodeEnum.ORDER_CREATE_SET_SELLER);
            }
        } else {
            // 用户自己下单（根据地址获取销售员）
            orderSellerId = getSellerIdByAddress(orderForm.getOrAddress());
            if (orderSellerId == null) {
                return new GenericResponse(ErrorCodeEnum.ORDER_CREATE_SELLER_NOT_EXIT);
            }
        }

        // 校验销售员是否存在
        SellerEntity sellerEntity = sellerMapper.selectById(orderSellerId);
        if (sellerEntity == null) {
            return new GenericResponse<>(ErrorCodeEnum.SELLER_NOT_FOUND);
        }

        // 订单状态：0未付款,1已付款，2待发货,3已发货,4已签收,5退货中,6已退货，7完成交易
        orderEntity.setOrStatus(OrderStatusEnum.UN_PAID.getCode());

        return joinActivity(orderEntity);
    }

    @Override
    public GenericResponse create(OrderForm orderForm) {
        GenericResponse previewResponse = preview(orderForm);
        if (!previewResponse.successful()) {
            return previewResponse;
        }

        OrderEntity orderEntity = (OrderEntity) previewResponse.getBody();
        orderMapper.insert(orderEntity);

        return new GenericResponse<>(orderEntity.getOrId());
    }

    @Override
    public GenericResponse modify(OrderForm orderForm) {
        OrderEntity orderEntity = orderMapper.selectById(orderForm.getOrId());
        if (orderEntity == null) {
            return new GenericResponse(ErrorCodeEnum.ORDER_NOT_FOUND);
        }
        // 订单状态，参考 OrderStatusEnum
        Integer orStatus = orderEntity.getOrStatus();

        // 1.未付款，订单信息均可修改
        if (OrderStatusEnum.UN_PAID.getCode().equals(orStatus)) {
            GenericResponse unpaidModifyOrder = unpaidModifyOrder(orderForm, orderEntity);
            if (!unpaidModifyOrder.successful()) {
                return unpaidModifyOrder;
            }
            orderEntity = (OrderEntity) unpaidModifyOrder.getBody();

        } else if (OrderStatusEnum.PAID.getCode().equals(orStatus)
                || OrderStatusEnum.UN_SHIPPED.getCode().equals(orStatus)) {
            // 2.已付款，未发货可修改收货地址和备注（暂只支持销售员修改地址，如果想让用户修改地址，可放开下边代码）
            Integer orderAddress = orderForm.getOrAddress();
            /*GenericResponse userModifyAddress = notShippedModifyAddress(orderEntity, orderAddress);
            if (!userModifyAddress.successful()) {
                return userModifyAddress;
            }
            orderEntity = (OrderEntity) userModifyAddress.getBody();*/

            // 订单已付款，仅支持修改收货地址
            orderEntity.setOrAddress(orderAddress);
            orderEntity.setOrWords(orderForm.getOrWords());

        } else {
            // 3.其他状态不允许修改
            return new GenericResponse(ErrorCodeEnum.ORDER_MODIFY_ERROR);
        }

        orderMapper.update(orderEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse orderCheck(OrderCheckForm orderCheckForm) {
        OrderEntity orderEntity = orderMapper.selectById(orderCheckForm.getOrId());
        if (orderEntity == null) {
            return new GenericResponse(ErrorCodeEnum.ORDER_NOT_FOUND);
        }
        if (orderEntity.getOrType() != 0) {
            return new GenericResponse(ErrorCodeEnum.ORDER_CHECKED);
        }
        if (OrderStatusEnum.UN_PAID.getCode().equals(orderEntity.getOrStatus())) {
            return new GenericResponse(ErrorCodeEnum.ORDER_CHECK_UNPAID);
        }
        if (!orderEntity.getOrSellerId().equals(orderCheckForm.getOrSellerId())) {
            return new GenericResponse(ErrorCodeEnum.ORDER_MODIFY_SELLER_ERROR);
        }

        orderEntity.setOrType(orderCheckForm.getOrType());
        if (ORDER_AUDIT_SUCCESS.equals(orderCheckForm.getOrType())) {
            // 审核通过，并且已付款，商品进入待发货
            if (OrderStatusEnum.PAID.getCode().equals(orderEntity.getOrStatus())) {
                orderEntity.setOrStatus(OrderStatusEnum.UN_SHIPPED.getCode());
            }
        } else if (ORDER_AUDIT_FAIL.equals(orderCheckForm.getOrType())) {
            orderEntity.setOrRefuse(orderCheckForm.getOrRefuse());
            orderEntity.setOrStatus(OrderStatusEnum.AFTER_SALE.getCode());
            // 审核拒绝，订单进入售后
            GenericResponse afterSale = afterSale(orderEntity);
            if (!afterSale.successful()) {
                return afterSale;
            }
        }
        orderMapper.update(orderEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse payment(PaymentForm paymentForm) {
        OrderEntity orderEntity = orderMapper.selectById(paymentForm.getOrderId());
        if (orderEntity == null) {
            return new GenericResponse(ErrorCodeEnum.ORDER_NOT_FOUND);
        }

        if (!OrderStatusEnum.UN_PAID.getCode().equals(orderEntity.getOrStatus())) {
            return new GenericResponse(ErrorCodeEnum.ORDER_PAYMENT_PAYED);
        }

        if (orderEntity.getOrTotalAmount().compareTo(paymentForm.getPaymentAmount()) != 0) {
            return new GenericResponse(ErrorCodeEnum.ORDER_PAYMENT_ERROR);
        }

        // 订单状态：0未付款,1已付款，2待发货,3已发货,4已签收,5退货中,6已退货，7完成交易
        if (orderEntity.getOrType() == 0) {
            orderEntity.setOrStatus(OrderStatusEnum.PAID.getCode());
        } else if (orderEntity.getOrType() == 1) {
            orderEntity.setOrStatus(OrderStatusEnum.UN_SHIPPED.getCode());
        }
        orderMapper.update(orderEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse process(OrderProcessForm orderProcessForm) {
        OrderEntity orderEntity = orderMapper.selectById(orderProcessForm.getOrId());
        if (orderEntity == null) {
            return new GenericResponse(ErrorCodeEnum.ORDER_NOT_FOUND);
        }

        // [售后完成/已完成评价]的订单不允许修改
        if (OrderStatusEnum.AFTER_SALE_FINISH.getCode().equals(orderEntity.getOrStatus())
                || OrderStatusEnum.ASSESSED_FINISH.getCode().equals(orderEntity.getOrStatus())) {
            return new GenericResponse(ErrorCodeEnum.ORDER_FINISH);
        }

        OrderStatusEnum orderStatusEnum = orderProcessForm.getStatus();
        if (orderStatusEnum == null) {
            return new GenericResponse(ErrorCodeEnum.ORDER_TRANSFER);
        }

        switch (orderStatusEnum) {
            // 发货/签收/售后
            case SHIPPED:
                if (OrderStatusEnum.UN_SHIPPED.getCode().equals(orderEntity.getOrStatus())) {
                    orderEntity.setOrStatus(orderStatusEnum.getCode());
                } else {
                    return new GenericResponse(ErrorCodeEnum.ORDER_STATUS_ERROR);
                }
                break;
            case SIGNING:
                if (OrderStatusEnum.SHIPPED.getCode().equals(orderEntity.getOrStatus())) {
                    orderEntity.setOrStatus(orderStatusEnum.getCode());
                } else {
                    return new GenericResponse(ErrorCodeEnum.ORDER_STATUS_ERROR);
                }
                break;
            case FINISH:
                if (OrderStatusEnum.SIGNING.getCode().equals(orderEntity.getOrStatus())) {
                    // 给用户添加积分
                    GenericResponse addCreditScore = addCreditScore(orderEntity.getOrUserId(), orderEntity.getOrGoodsId(), orderEntity.getOrCount());
                    if (!addCreditScore.successful()) {
                        return addCreditScore;
                    }
                    orderEntity.setOrStatus(orderStatusEnum.getCode());
                } else {
                    return new GenericResponse(ErrorCodeEnum.ORDER_STATUS_ERROR);
                }
                break;

            // 申请售后
            case AFTER_SALE:
                GenericResponse afterSale = afterSale(orderEntity);
                if (afterSale.successful()) {
                    orderEntity.setOrStatus(OrderStatusEnum.AFTER_SALE.getCode());
                    orderEntity.setOrWords(orderProcessForm.getSaleMessage());
                    break;
                } else {
                    return afterSale;
                }

            case AFTER_SALE_FINISH:
                // 售后完成
                GenericResponse saleFinish = saleFinish(orderEntity,
                        orderProcessForm.getAfterSaleType(), orderProcessForm.getSaleMessage());
                if (saleFinish.successful()) {
                    orderEntity.setOrStatus(OrderStatusEnum.AFTER_SALE_FINISH.getCode());
                    orderEntity.setOrWords(orderProcessForm.getSaleMessage());
                    break;
                } else {
                    return saleFinish;
                }
            default:
                break;
        }

        orderMapper.update(orderEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse findByExample(OrderSearch orderSearch) {
        if (orderSearch.getOrId() != null) {
            OrderEntity orderEntity = orderMapper.selectById(orderSearch.getOrId());
            if (orderEntity == null) {
                return new GenericResponse(ErrorCodeEnum.ORDER_NOT_FOUND);
            }
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(orderEntity, orderVO);
            GoodsEntity goodsEntity = goodsMapper.selectById(orderEntity.getOrGoodsId());
            if (goodsEntity != null) {
                orderVO.setName(goodsEntity.getName());
                orderVO.setNewImgPath(goodsEntity.getNewImgPath());
            }
            return new GenericResponse<>(orderVO);
        }
        if (StringUtils.isNotBlank(orderSearch.getPhoneNo())) {
            UserEntity userEntity = userMapper.selectByPhoneNo(orderSearch.getPhoneNo());
            if (userEntity != null) {
                orderSearch.setOrUserId(userEntity.getId());
            }
        }

        List<OrderEntity> orderEntities = orderMapper.selectListByExample(orderSearch);
        List<OrderVO> orderVOS = parseOrderVOS(orderEntities);

        return new GenericResponse<>(orderVOS);
    }

    @Override
    public GenericResponse findPageByExample(OrderSearch orderSearch) {
        if (StringUtils.isNotBlank(orderSearch.getPhoneNo())) {
            UserEntity userEntity = userMapper.selectByPhoneNo(orderSearch.getPhoneNo());
            if (userEntity != null) {
                orderSearch.setOrUserId(userEntity.getId());
            }
        }
        List<OrderEntity> orderEntities = orderMapper.selectByPage(orderSearch);
        Integer total = orderMapper.selectByPageTotal(orderSearch);

        return genericPageResponse(parseOrderVOS(orderEntities), total);
    }

    @Override
    public GenericResponse getTotalAmountByMonth() {
        List<AmountSearch> amountByMonth = orderMapper.getTotalAmountByMonth();
        return new GenericResponse<>(amountByMonth);
    }

    @Override
    public GenericResponse getTotalAmount() {
        List<AmountSearch> amountByMonth = orderMapper.getTotalAmountByMonth();
        double totalAmount = amountByMonth.stream().mapToDouble(AmountSearch::getAmt).sum();
        return new GenericResponse<>(totalAmount);
    }

    @Override
    public GenericResponse orderAddress(OrderAddressForm orderAddressForm) {
        AddressEntity addressEntity = new AddressEntity();

        if (orderAddressForm.getPhone() != null) {
            UserEntity userEntity = userMapper.selectByPhoneNo(orderAddressForm.getPhone() + "");
            if (userEntity != null) {
                addressEntity.setUid(userEntity.getId());
            }
        }

        addressEntity.setUname(orderAddressForm.getName());
        addressEntity.setPhone(orderAddressForm.getPhone());
        addressEntity.setInfo(orderAddressForm.getInfo());
        addressEntity.setAddress(orderAddressForm.getAddress());
        addressEntity.setStatus(0);
        addressMapper.insert(addressEntity);

        return new GenericResponse<>(addressEntity);
    }

    @Override
    public GenericResponse orderAddress(Long id) {

        return new GenericResponse<>(addressMapper.selectById(id));
    }

    @Override
    public GenericResponse afterSalePage(CustomerSearch customerSearch) {
        List<CustomerEntity> customerEntities = customerMapper.selectByPage(customerSearch);
        Integer total = customerMapper.selectByPageTotal(customerSearch);

        return genericPageResponse(parseCustomerVOS(customerEntities), total);
    }

    /**
     * 获取订单信息
     *
     * @param orderEntities List<OrderEntity>
     * @return List<OrderVO>
     */
    private List<OrderVO> parseOrderVOS(List<OrderEntity> orderEntities) {
        Set<Long> goodsIds = new TreeSet<>();
        List<OrderVO> orderVOS = new ArrayList<>();
        orderEntities.forEach(orderEntity -> {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(orderEntity, orderVO);
            orderVOS.add(orderVO);
            goodsIds.add(orderEntity.getOrGoodsId());
        });

        GoodsSearch goodsSearch = new GoodsSearch();
        goodsSearch.setIds(goodsIds);
        List<GoodsEntity> goodsEntities = goodsMapper.selectListByExample(goodsSearch);

        Map<Long, GoodsEntity> goodsEntityMap = new HashMap<>(goodsEntities.size());
        goodsEntities.forEach(goodsEntity -> goodsEntityMap.put(goodsEntity.getId(), goodsEntity));

        orderVOS.forEach(orderVO -> {
            GoodsEntity goodsEntity = goodsEntityMap.get(orderVO.getOrGoodsId());
            if (goodsEntity == null) {
                return;
            }
            orderVO.setName(goodsEntity.getName());
            orderVO.setNewImgPath(goodsEntity.getNewImgPath());
        });
        return orderVOS;
    }

    /**
     * 获取售后信息
     *
     * @param customerEntities List<CustomerEntity>
     * @return List<CustomerVO>
     */
    private List<CustomerVO> parseCustomerVOS(List<CustomerEntity> customerEntities) {
        Set<Long> goodsIds = new TreeSet<>();
        List<CustomerVO> customerVOS = new ArrayList<>();
        customerEntities.forEach(customerEntity -> {
            CustomerVO orderVO = new CustomerVO();
            BeanUtils.copyProperties(customerEntity, orderVO);
            customerVOS.add(orderVO);
            goodsIds.add(customerEntity.getCuGoodsId());
        });

        List<GoodsEntity> goodsEntities = goodsMapper.selectListByExample(new GoodsSearch().setIds(goodsIds));
        Map<Long, GoodsEntity> goodsEntityMap = new HashMap<>(goodsEntities.size());
        goodsEntities.forEach(goodsEntity -> goodsEntityMap.put(goodsEntity.getId(), goodsEntity));

        customerVOS.forEach(customerVO -> {
            GoodsEntity goodsEntity = goodsEntityMap.get(customerVO.getCuGoodsId());
            if (goodsEntity == null) {
                return;
            }
            customerVO.setName(goodsEntity.getName());
            customerVO.setNewImgPath(goodsEntity.getNewImgPath());
        });
        return customerVOS;
    }

    /**
     * 添加用户积分
     *
     * @param userId  用户编号
     * @param goodsId 购买的商品编号
     * @param count   购买的商品数量
     * @return GenericResponse
     */
    private GenericResponse addCreditScore(Long userId, Long goodsId, Integer count) {
        UserEntity userEntity = userMapper.selectById(userId);
        if (userEntity == null) {
            return new GenericResponse(ErrorCodeEnum.USER_NOT_FOUND);
        }
        GoodsEntity goodsEntity = goodsMapper.selectById(goodsId);
        if (goodsEntity == null) {
            return new GenericResponse(ErrorCodeEnum.GOODS_NOT_FOUND);
        }
        Integer goodsCredit = goodsEntity.getCredit();

        Integer creditScore = goodsCredit * count;

        // 用户原有积分
        Long credit = userEntity.getCredit();
        credit += creditScore;
        userEntity.setCredit(credit);

        userMapper.update(userEntity);

        return GenericResponse.SUCCESS;
    }

    /**
     * 根据地址获取指定销售员
     *
     * @param addressId 地址ID
     * @return 销售员ID
     */
    private Long getSellerIdByAddress(Integer addressId) {
        AddressEntity addressEntity = addressMapper.selectById(addressId);
        if (addressEntity == null) {
            return null;
        }
        String address = addressEntity.getAddress();
        JSONObject jsonObject = JSON.parseObject(address);
        Long townCode = jsonObject.getLong("towncode");
        AreaEntity areaEntity = areaMapper.selectOne(townCode);
        if (areaEntity == null) {
            return null;
        }
        return areaEntity.getSpid();
    }

    /**
     * 根据销售员下单或者用户下单获取用户信息
     *
     * @param orderForm OrderForm
     * @return UserEntity
     */
    private UserEntity getUserEntity(OrderForm orderForm) {
        UserEntity userEntity;
        if (orderForm.getOrderFlag()) {
            userEntity = userMapper.selectByPhoneNo(orderForm.getPhoneNo());
        } else {
            userEntity = userMapper.selectById(orderForm.getOrUserId());
        }
        return userEntity;
    }

    /**
     * 校验商品是否存在或已下架
     *
     * @param orGoodsId 商品ID
     * @return 商品状态
     */
    private GenericResponse checkGoods(Long orGoodsId) {
        GoodsEntity goodsEntity = goodsMapper.selectById(orGoodsId);
        if (goodsEntity == null) {
            return new GenericResponse(ErrorCodeEnum.GOODS_NOT_FOUND);
        }
        // 判断商品是否已下架
        if (goodsEntity.getIsShow() != 1) {
            return new GenericResponse(ErrorCodeEnum.GOODS_REMOVED);
        }
        return new GenericResponse<>(goodsEntity);
    }

    /**
     * 订单未支付修改订单信息
     *
     * @param orderForm   要修改的订单信息
     * @param orderEntity 修改后的订单
     * @return 修改状态
     */
    private GenericResponse unpaidModifyOrder(OrderForm orderForm, OrderEntity orderEntity) {
        /*
         * 校验用户
         */
        UserEntity userEntity = getUserEntity(orderForm);
        if (userEntity == null) {
            return new GenericResponse<>(ErrorCodeEnum.USER_NOT_FOUND);
        }
        if (!userEntity.getId().equals(orderEntity.getOrUserId())) {
            orderForm.setOrUserId(userEntity.getId());
        }

        /*
         * 校验销售员
         * 1.客户修改订单，要校验地址和该地区的销售员，如下 A、B
         * 2.销售员修改订单，要校验新的销售员，如下 B
         */
        Long orderSellerId = orderForm.getOrSellerId();

        // A.客户下单，并且修改了地址，此时要更新指定销售员
        if (!orderForm.getOrderFlag() && !orderForm.getOrAddress().equals(orderEntity.getOrAddress())) {
            orderSellerId = getSellerIdByAddress(orderForm.getOrAddress());
            if (orderSellerId == null) {
                return new GenericResponse(ErrorCodeEnum.ORDER_CREATE_SELLER_NOT_EXIT);
            }
        }

        // B.销售员下单，或用户修改了地址，此时要校验销售员编号和之前是否一样
        if (!orderSellerId.equals(orderEntity.getOrSellerId())) {
            // 校验销售员
            SellerEntity sellerEntity = sellerMapper.selectById(orderSellerId);
            if (sellerEntity == null) {
                return new GenericResponse<>(ErrorCodeEnum.SELLER_NOT_FOUND);
            }
            orderEntity.setOrSellerId(orderSellerId);
        }

        /*
         * 校验商品
         */
        GenericResponse checkGoodsResponse = checkGoods(orderForm.getOrGoodsId());
        if (!checkGoodsResponse.successful()) {
            return checkGoodsResponse;
        }
        GoodsEntity goodsEntity = (GoodsEntity) checkGoodsResponse.getBody();
        if (orderForm.getOrCount() < 1) {
            return new GenericResponse<>(ErrorCodeEnum.ORDER_MODIFY_COUNT_MIN_1);
        }

        /*
         * 接口安全：重新设置金额(单价 * 数量)
         */
        BigDecimal goodsCount = new BigDecimal(orderForm.getOrCount());
        orderEntity.setOrPrice(goodsEntity.getPrice());
        orderEntity.setOrTotalAmount(goodsEntity.getPrice().multiply(goodsCount));

        /*
         * 设置其他信息
         */
        BeanUtils.copyProperties(orderForm, orderEntity);

        return joinActivity(orderEntity);
    }

    /**
     * 参加活动
     *
     * @param orderEntity 订单信息
     * @return 参加状态
     */
    private GenericResponse joinActivity(OrderEntity orderEntity) {
        Long activityId = orderEntity.getOrSay();
        // 没有传活动ID
        if (activityId == null) {
            return new GenericResponse<>(orderEntity);
        }

        // 根据活动ID、订单原价，获取活动价
        GenericResponse<BigDecimal> joinActivityResponse = activityBusiness.joinActivity(orderEntity.getOrTotalAmount(), activityId);
        if (joinActivityResponse.successful()) {
            orderEntity.setOrTotalAmount(joinActivityResponse.getBody());
            orderEntity.setOrSay(activityId);
            return new GenericResponse<>(orderEntity);
        }

        return joinActivityResponse;
    }

    /**
     * 申请售后
     * 现仅支持（退货/审核拒绝退款）
     *
     * @param orderEntity OrderEntity
     * @return GenericResponse
     */
    private GenericResponse afterSale(OrderEntity orderEntity) {
        // 订单编号
        Long orderId = orderEntity.getOrId();
        // 售后原因：1退货（默认），2审核拒绝退款
        int reason = ORDER_SALE_RETURN;
        if (ORDER_AUDIT_FAIL.equals(orderEntity.getOrType())) {
            reason = ORDER_SALE_REFUND;
        }
        CustomerEntity customerEntity = customerMapper.selectByOrder(orderId, reason);
        if (customerEntity != null) {
            if (reason == ORDER_SALE_REFUND) {
                return new GenericResponse(ErrorCodeEnum.ORDER_AFTER_SALE_REFUND_PROCESSING);
            } else {
                return new GenericResponse(ErrorCodeEnum.ORDER_AFTER_SALE_PROCESSING);
            }
        }

        customerEntity = new CustomerEntity();
        customerEntity.setCuOrderId(orderEntity.getOrId());
        customerEntity.setCuUserId(orderEntity.getOrUserId());
        customerEntity.setCuSellerId(orderEntity.getOrSellerId());
        customerEntity.setCuGoodsId(orderEntity.getOrGoodsId());
        customerEntity.setCuGoodsCount(orderEntity.getOrCount());
        // 售后原因
        customerEntity.setCuReason(reason);
        // 操作类型：1申请提交 | 2申请通过 | 3申请拒绝
        customerEntity.setCuType(1);
        customerEntity.setCuTime(new Date());
        customerEntity.setCuMoney(orderEntity.getOrTotalAmount());

        customerMapper.insert(customerEntity);

        return GenericResponse.SUCCESS;
    }

    /**
     * 售后完成
     *
     * @param orderEntity OrderEntity
     * @return GenericResponse
     */
    private GenericResponse saleFinish(OrderEntity orderEntity, Integer cuType, String description) {
        // 售后原因：1退货（默认），2审核拒绝退款
        int reason = ORDER_SALE_RETURN;
        if (ORDER_AUDIT_FAIL.equals(orderEntity.getOrType())) {
            reason = ORDER_SALE_REFUND;
        }
        CustomerEntity customerEntity = customerMapper.selectByOrder(orderEntity.getOrId(), reason);
        if (customerEntity == null) {
            return new GenericResponse(ErrorCodeEnum.ORDER_AFTER_SALE_NOT_FOUND);
        }
        // 操作类型：1申请提交 | 2申请通过 | 3申请拒绝
        customerEntity.setCuType(cuType);
        customerEntity.setCuTime(new Date());
        customerEntity.setCuDescription(description);

        customerMapper.update(customerEntity);

        return GenericResponse.SUCCESS;
    }

    /**
     * 未发货修改收货地址 >>> (只适用于用户修改收货地址) 暂时用不到
     *
     * @param orderEntity  修改后的订单信息
     * @param orderAddress 收货地址
     * @return 修改收货地址结果
     */
    @SuppressWarnings("ALL")
    private GenericResponse notShippedModifyAddress(OrderEntity orderEntity, Integer orderAddress) {
        // 修改了地址，此时要更新指定销售员
        if (!orderAddress.equals(orderEntity.getOrAddress())) {
            Long orderSellerId = getSellerIdByAddress(orderAddress);
            if (orderSellerId == null) {
                return new GenericResponse(ErrorCodeEnum.ORDER_CREATE_SELLER_NOT_EXIT);
            }
            // 校验销售员
            SellerEntity sellerEntity = sellerMapper.selectById(orderSellerId);
            if (sellerEntity == null) {
                return new GenericResponse<>(ErrorCodeEnum.SELLER_NOT_FOUND);
            }
            orderEntity.setOrSellerId(orderSellerId);
            orderEntity.setOrAddress(orderAddress);
        }
        return new GenericResponse<>(orderEntity);
    }
}
