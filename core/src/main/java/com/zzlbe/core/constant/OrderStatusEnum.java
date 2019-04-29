package com.zzlbe.core.constant;

/**
 * PROJECT: Sales
 * DESCRIPTION: 订单状态枚举
 *
 * @author amos
 * @date 2019/4/29
 */
public enum OrderStatusEnum {

    /***/
    CREATE("已创建"),
    CHECK("已审核"),
    PAYMENT("已支付"),
    SHIP("已发货"),
    SIGNING("已签收"),
    AFTER_SALE("售后处理中"),
    FINISH_SALE("售后处理完成"),
    FINISH("交易完成");

    private final String status;

    OrderStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
