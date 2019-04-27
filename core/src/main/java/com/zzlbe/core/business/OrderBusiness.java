package com.zzlbe.core.business;

import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.OrderCheckForm;
import com.zzlbe.core.request.OrderForm;
import com.zzlbe.core.request.PaymentForm;
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

}
