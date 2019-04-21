package com.zzlbe.core.business;

import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.OrderForm;
import com.zzlbe.core.request.PaymentForm;

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
     * 订单支付
     *
     * @param paymentForm OrderForm
     * @return GenericResponse
     */
    GenericResponse payment(PaymentForm paymentForm);

}
