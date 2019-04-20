package com.zzlbe.core.business.impl;

import com.zzlbe.core.business.OrderBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.OrderForm;
import com.zzlbe.core.request.PaymentForm;
import com.zzlbe.dao.entity.GoodsEntity;
import com.zzlbe.dao.entity.OrderEntity;
import com.zzlbe.dao.entity.UserEntity;
import com.zzlbe.dao.mapper.GoodsMapper;
import com.zzlbe.dao.mapper.OrderMapper;
import com.zzlbe.dao.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * PROJECT: sales management
 * DESCRIPTION: 订单相关业务实现
 *
 * @author amos
 * @date 2019/4/20
 */
@Component("orderBusiness")
public class OrderBusinessImpl implements OrderBusiness {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public GenericResponse preview(OrderForm orderForm) {
        return null;
    }

    @Override
    public GenericResponse create(OrderForm orderForm) {
        // 判断下单用户是否存在
        Long orUserId = orderForm.getOrUserId();
        UserEntity userEntity = userMapper.selectById(orUserId);
        if (userEntity == null) {
            return new GenericResponse("4000", "用户不存在");
        }
        // 判断商品是否存在
        Long orGoodsId = orderForm.getOrGoodsId();
        GoodsEntity goodsEntity = goodsMapper.selectById(orGoodsId);
        if (goodsEntity == null) {
            return new GenericResponse("4001", "商品不存在");
        }
        // 判断商品是否已下架
        if (goodsEntity.getIsShow() == 1) {
            return new GenericResponse("4002", "商品已下架");
        }

        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderForm, orderEntity);
        orderEntity.setOrDatetime(new Date());
        // 下单审核状态：0待审核，1审核通过，2审核拒绝
        orderEntity.setOrType(0);
        // 订单状态：0未付款,1已付款，2待发货,3已发货,4已签收,5退货中,6已退货，7完成交易
        orderEntity.setOrStatus(0);

        orderMapper.insert(orderEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse modify(OrderForm orderForm) {
        OrderEntity orderEntity = orderMapper.selectById(orderForm.getOrId());
        if (orderEntity == null) {
            return new GenericResponse("4010", "订单不存在");
        }

        BeanUtils.copyProperties(orderForm, orderEntity);
        orderMapper.update(orderEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse payment(PaymentForm paymentForm) {
        OrderEntity orderEntity = orderMapper.selectById(paymentForm.getOrderId());
        if (orderEntity == null) {
            return new GenericResponse("4010", "订单不存在");
        }

        if (!orderEntity.getOrTotalAmount().equals(paymentForm.getPaymentAmount())) {
            return new GenericResponse("4021", "仅支持全额付款");
        }

        // 订单状态：0未付款,1已付款，2待发货,3已发货,4已签收,5退货中,6已退货，7完成交易
        orderEntity.setOrStatus(1);
        orderMapper.update(orderEntity);

        return GenericResponse.SUCCESS;
    }
}
