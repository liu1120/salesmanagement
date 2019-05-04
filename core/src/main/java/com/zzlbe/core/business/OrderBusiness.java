package com.zzlbe.core.business;

import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.*;
import com.zzlbe.dao.search.CustomerSearch;
import com.zzlbe.dao.search.OrderSearch;

/**
 * PROJECT: sales management
 * DESCRIPTION: 订单相关业务
 *
 * @author amos
 * @date 2019/4/20
 */
public interface OrderBusiness {

    /**
     * 订单预览
     *
     * @param orderForm OrderForm
     * @return GenericResponse
     */
    GenericResponse preview(OrderForm orderForm);

    /**
     * 订单创建
     *
     * @param orderForm OrderForm
     * @return GenericResponse
     */
    GenericResponse create(OrderForm orderForm);

    /**
     * 订单修改
     *
     * @param orderForm OrderForm
     * @return GenericResponse
     */
    GenericResponse modify(OrderForm orderForm);

    /**
     * 订单审核
     *
     * @param orderCheckForm 订单审核表单
     * @return GenericResponse
     */
    GenericResponse orderCheck(OrderCheckForm orderCheckForm);

    /**
     * 订单支付
     *
     * @param paymentForm OrderForm
     * @return GenericResponse
     */
    GenericResponse payment(PaymentForm paymentForm);

    /**
     * 订单正常流程
     *
     * @param orderProcessForm 订单进度表单
     * @return GenericResponse
     */
    GenericResponse process(OrderProcessForm orderProcessForm);

    /**
     * 查询订单
     *
     * @param orderSearch 查询条件
     * @return GenericResponse
     */
    GenericResponse findByExample(OrderSearch orderSearch);

    /**
     * 查询订单
     *
     * @param orderSearch 查询条件
     * @return GenericResponse
     */
    GenericResponse findPageByExample(OrderSearch orderSearch);

    /**
     * 统计近12月销售额
     *
     * @return GenericResponse
     */
    GenericResponse getTotalAmountByMonth();

    /**
     * 统计当月销售额
     *
     * @return GenericResponse
     */
    GenericResponse getTotalAmount();

    /**
     * 添加收货地址
     *
     * @param orderAddressForm 收货地址表单
     * @return GenericResponse
     */
    GenericResponse orderAddress(OrderAddressForm orderAddressForm);

    /**
     * 获取收货地址
     *
     * @param id 地址id
     * @return GenericResponse
     */
    GenericResponse orderAddress(Long id);

    /**
     * 售后列表
     *
     * @param customerSearch CustomerSearch
     * @return GenericResponse
     */
    GenericResponse afterSalePage(CustomerSearch customerSearch);
}
