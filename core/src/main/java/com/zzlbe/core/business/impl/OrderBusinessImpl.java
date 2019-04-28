package com.zzlbe.core.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzlbe.core.business.OrderBusiness;
import com.zzlbe.core.common.ErrorCodeEnum;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.OrderCheckForm;
import com.zzlbe.core.request.OrderForm;
import com.zzlbe.core.request.PaymentForm;
import com.zzlbe.dao.entity.*;
import com.zzlbe.dao.mapper.*;
import com.zzlbe.dao.search.AmountSearch;
import com.zzlbe.dao.search.OrderSearch;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private UserMapper userMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private SellerMapper sellerMapper;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private AreaMapper areaMapper;


    @Override
    public GenericResponse preview(OrderForm orderForm) {
        // 校验用户
        UserEntity userEntity = userMapper.selectById(orderForm.getOrUserId());
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
        orderEntity.setOrStatus(0);

        return new GenericResponse<>(orderEntity);
    }

    @Override
    public GenericResponse create(OrderForm orderForm) {
        GenericResponse previewResponse = preview(orderForm);
        if (!previewResponse.successful()) {
            return previewResponse;
        }

        orderMapper.insert((OrderEntity) previewResponse.getBody());

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse modify(OrderForm orderForm) {
        OrderEntity orderEntity = orderMapper.selectById(orderForm.getOrId());
        if (orderEntity == null) {
            return new GenericResponse(ErrorCodeEnum.ORDER_NOT_FOUND);
        }

        // 订单状态：0未付款,1已付款，2待发货,3已发货,4已签收,5退货中,6已退货，7完成交易
        Integer orStatus = orderEntity.getOrStatus();

        // 1.未付款，订单信息均可修改
        if (orStatus == 0) {
            GenericResponse unpaidModifyOrder = unpaidModifyOrder(orderForm, orderEntity);
            if (!unpaidModifyOrder.successful()) {
                return unpaidModifyOrder;
            }
            orderEntity = (OrderEntity) unpaidModifyOrder.getBody();

        } else if (orStatus == 1 || orStatus == 2) {
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
        if (!orderEntity.getOrSellerId().equals(orderCheckForm.getOrSellerId())) {
            return new GenericResponse(ErrorCodeEnum.ORDER_MODIFY_SELLER_ERROR);
        }

        orderEntity.setOrType(orderCheckForm.getOrType());
        orderEntity.setOrRefuse(orderCheckForm.getOrRefuse());
        orderMapper.update(orderEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse payment(PaymentForm paymentForm) {
        OrderEntity orderEntity = orderMapper.selectById(paymentForm.getOrderId());
        if (orderEntity == null) {
            return new GenericResponse(ErrorCodeEnum.ORDER_NOT_FOUND);
        }

        if (orderEntity.getOrType() != 1) {
            return new GenericResponse(ErrorCodeEnum.ORDER_PAYMENT_CHECK_ERROR);
        }

        if (!orderEntity.getOrTotalAmount().equals(paymentForm.getPaymentAmount())) {
            return new GenericResponse(ErrorCodeEnum.ORDER_PAYMENT_ERROR);
        }

        // 订单状态：0未付款,1已付款，2待发货,3已发货,4已签收,5退货中,6已退货，7完成交易
        orderEntity.setOrStatus(1);
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
            return new GenericResponse<>(orderEntity);
        }

        List<OrderEntity> orderEntities = orderMapper.selectListByExample(orderSearch);

        return new GenericResponse<>(orderEntities);
    }

    @Override
    public GenericResponse findPageByExample(OrderSearch orderSearch) {
        List<OrderEntity> orderEntities = orderMapper.selectByPage(orderSearch);
        Integer total = orderMapper.selectByPageTotal(orderSearch);

        return genericPageResponse(orderEntities, total);
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
        Long townCode = jsonObject.getLong("town_code");
        AreaEntity areaEntity = areaMapper.selectOne(townCode);
        if (areaEntity == null) {
            return null;
        }
        return areaEntity.getSpid();
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
        if (!orderForm.getOrUserId().equals(orderEntity.getOrUserId())) {
            UserEntity userEntity = userMapper.selectById(orderForm.getOrUserId());
            if (userEntity == null) {
                return new GenericResponse<>(ErrorCodeEnum.USER_NOT_FOUND);
            }
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

        return new GenericResponse<>(orderEntity);
    }

    /**
     * 未发货修改收货地址 >>> (只适用于用户修改收货地址) 暂时用不到
     *
     * @param orderEntity  修改后的订单信息
     * @param orderAddress 收货地址
     * @return 修改收货地址结果
     */
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
