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
     * 订单状态
     */
    private OrderStatusEnum status;

    /**
     * 售后相关：售后留言
     */
    private String saleMessage;

    /**
     * 售后处理相关：2审核通过 || 3审核拒绝
     */
    private Integer afterSaleType;
}
