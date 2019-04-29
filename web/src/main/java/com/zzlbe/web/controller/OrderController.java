package com.zzlbe.web.controller;

import com.zzlbe.core.business.OrderBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.OrderCheckForm;
import com.zzlbe.core.request.OrderForm;
import com.zzlbe.core.request.OrderProcessForm;
import com.zzlbe.core.request.PaymentForm;
import com.zzlbe.dao.search.OrderSearch;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * PROJECT: sales management
 * DESCRIPTION: 订单相关
 *
 * @author amos
 * @date 2019/4/20
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderBusiness orderBusiness;

    /**
     * 预览
     */
    @RequestMapping("preview")
    public GenericResponse preview(@RequestBody @Valid OrderForm orderForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return GenericResponse.ERROR.setMessage(list.get(0).getDefaultMessage());
        }

        return orderBusiness.preview(orderForm);
    }

    /**
     * 下单
     */
    @RequestMapping("create")
    public GenericResponse create(@RequestBody @Valid OrderForm orderForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            return GenericResponse.ERROR.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        return orderBusiness.create(orderForm);
    }

    /**
     * 修改订单
     */
    @RequestMapping("modify")
    public GenericResponse modify(@RequestBody OrderForm orderForm) {

        return orderBusiness.modify(orderForm);
    }

    /**
     * 审核订单
     */
    @RequestMapping("orderCheck")
    public GenericResponse orderCheck(@RequestBody OrderCheckForm orderCheckForm) {

        return orderBusiness.orderCheck(orderCheckForm);
    }

    /**
     * 订单支付
     */
    @RequestMapping("payment")
    public GenericResponse payment(@RequestBody PaymentForm paymentForm) {

        return orderBusiness.payment(paymentForm);
    }

    /**
     * 订单发货
     */
    @RequestMapping("process")
    public GenericResponse process(@RequestBody OrderProcessForm processForm) {

        return orderBusiness.process(processForm);
    }

    /**
     * 普通查询
     */
    @PostMapping("findByExample")
    public GenericResponse findByExample(@RequestBody OrderSearch orderSearch) {
        return orderBusiness.findByExample(orderSearch);
    }

    /**
     * 分页查询
     */
    @PostMapping("findByPage")
    public GenericResponse findByPage(@RequestBody OrderSearch orderSearch) {
        return orderBusiness.findPageByExample(orderSearch);
    }

    @RequestMapping("getTotalAmountByMonth")
    public GenericResponse getTotalAmountByMonth() {
        return orderBusiness.getTotalAmountByMonth();
    }

    @RequestMapping("getTotalAmount")
    public GenericResponse getTotalAmount() {
        return orderBusiness.getTotalAmount();
    }

}
