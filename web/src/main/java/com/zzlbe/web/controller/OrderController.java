package com.zzlbe.web.controller;

import com.zzlbe.core.business.OrderBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.OrderForm;
import com.zzlbe.core.request.PaymentForm;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * PROJECT: sales management
 * DESCRIPTION: 订单相关
 *
 * @author amos
 * @date 2019/4/20
 */
@RestController
@RequestMapping("order")
@CrossOrigin("http://localhost:8080")
public class OrderController {

    @Resource
    private OrderBusiness orderBusiness;

    @RequestMapping("preview")
    public GenericResponse preview(@RequestBody OrderForm orderForm) {

        return orderBusiness.preview(orderForm);
    }

    @RequestMapping("create")
    public GenericResponse create(@RequestBody OrderForm orderForm) {

        return orderBusiness.create(orderForm);
    }

    @RequestMapping("modify")
    public GenericResponse modify(@RequestBody OrderForm orderForm) {

        return orderBusiness.modify(orderForm);
    }

    @RequestMapping("payment")
    public GenericResponse payment(@RequestBody PaymentForm paymentForm) {

        return orderBusiness.payment(paymentForm);
    }

}
