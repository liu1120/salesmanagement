package com.zzlbe.core.request;

import com.zzlbe.core.constant.OrderStatusEnum;
import lombok.Data;

/**
 * PROJECT: Sales
 * DESCRIPTION: 订单历程表单
 *
 * @author amos
 * @date 2019/4/29
 */
@Data
public class OrderProcessForm {

    /**
     * 订单编号
     */
    private Long orId;
    /**
     * 下单留言
     */
    private String orWords;
    /**
     * 订单状态
     */
    private OrderStatusEnum status;

}
