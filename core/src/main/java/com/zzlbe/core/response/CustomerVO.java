package com.zzlbe.core.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * PROJECT: Sales
 * DESCRIPTION: 订单退换货表
 *
 * @author duGraceful
 * @date 2019/4/13
 */
@Data
public class CustomerVO {

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
    /**
     * 销售员编号
     */
    private Long cuSellerId;
    /**
     * 商品ID
     */
    private Long cuGoodsId;
    /**
     * 商品数量
     */
    private Integer cuGoodsCount;
    /**
     * 1退货，2审核拒绝退款
     */
    private Integer cuReason;
    /**
     * 操作类型：1业务操作人员申请提交、2业务操作人员审核通过、3业务操作人员审核拒绝
     */
    private Integer cuType;
    /**
     * 操作时间
     */
    private Date cuTime;
    /**
     * 业务操作描述（客服拒绝原因）
     */
    private String cuDescription;
    /**
     * 退款金额
     */
    private BigDecimal cuMoney;


    /**
     * 农化产品名字
     */
    private String name;
    /**
     * 农化产品图片存放路径
     */
    private String newImgPath;

}
