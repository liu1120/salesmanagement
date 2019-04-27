package com.zzlbe.core.request;

import lombok.Data;

/**
 * PROJECT: sales
 * DESCRIPTION: 订单相关FORM
 *
 * @author amos
 * @date 2019/4/20
 */
@Data
public class OrderCheckForm {

    /**
     * 订单编号
     */
    private Long orId;
    /**
     * 销售员编号
     */
    private Long orSellerId;
    /**
     * 下单审核状态：0待审核，1审核通过，2审核拒绝
     */
    private Integer orType;
    /**
     * 拒绝理由
     */
    private String orRefuse;

}
