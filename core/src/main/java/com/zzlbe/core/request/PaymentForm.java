package com.zzlbe.core.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * PROJECT: sales management
 * DESCRIPTION: 支付相关
 *
 * @author amos
 * @date 2019/4/20
 */
@Data
public class PaymentForm {

    /**
     * 订单编号
     */
    private Long orderId;
    /**
     * 支付渠道[支付宝/微信/银行卡/现金]
     */
    private String paymentWay;
    /**
     * 支付金额
     */
    private BigDecimal paymentAmount;

}
