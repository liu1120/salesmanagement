package com.zzlbe.dao.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
public class Customer2Search extends BasePageRequest {
    /**
     * 自增ID
     */
    private Long cuId;
    /**
     * 订单编号
     */
    private Long cuOrderId;
    /**
     * 用户编号
     */
    private Long cuUserId;
    private String cuUserName;
    /**
     * 销售员编号
     */
    private Long cuSellerId;
    private String cuSellerName;
    /**
     * 商品ID
     */
    private Long cuGoodsId;
    private String cuGoodsName;
    /**
     * 商品数量
     */
    private Integer cuGoodsCount;
    /**
     * 1退货，2审核拒绝退款
     */
    private String cuReason;
    /**
     * 操作类型：1业务操作人员申请提交、2业务操作人员审核通过、3业务操作人员审核拒绝
     */
    private Integer cuType;
    /**
     * 操作时间
     */
    private String cuTime;
    /**
     * 业务操作描述（客服拒绝原因）
     */
    private String cuDescription;
    /**
     * 退款金额
     */
    private BigDecimal cuMoney;

}
