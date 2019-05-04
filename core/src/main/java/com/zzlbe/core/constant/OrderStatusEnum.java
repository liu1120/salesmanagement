package com.zzlbe.core.constant;

/**
 * PROJECT: Sales
 * DESCRIPTION: 订单状态枚举
 *
 * @author amos
 * @date 2019/4/29
 */
public enum OrderStatusEnum {

    /**
     * @see com.zzlbe.dao.entity.OrderEntity#orStatus
     * 订单状态：0未付款，1已付款，2待发货，3已发货，4已签收，5退货中，6已退货，7完成交易，8已完成评价
     */
    UN_PAID(0, "未付款"),
    PAID(1, "已付款"),
    UN_SHIPPED(2, "待发货"),
    SHIPPED(3, "已发货"),
    SIGNING(4, "已签收"),
    AFTER_SALE(5, "售后处理中"),
    AFTER_SALE_FINISH(6, "售后完成"),
    FINISH(7, "完成交易"),
    ASSESSED_FINISH(8, "已完成评价");

    private final Integer code;
    private final String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
