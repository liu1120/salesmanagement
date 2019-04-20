package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * PROJECT: sales management
 * DESCRIPTION: 退货查询
 *
 * @author amos
 * @date 2019/4/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerSearch extends BasePageRequest {

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
    private Long cuGoodsCount;
    /**
     * 1退货
     */
    private Integer cuReason;
    /**
     * 操作类型：1业务操作人员申请提交、2业务操作人员审核通过、3业务操作人员审核拒绝
     */
    private Integer cuType;
    /**
     * 操作时间
     */
    private String cuTimeStr;
    /**
     * 业务操作描述（客服拒绝原因）
     */
    private String cuDescription;
    /**
     * 退款金额
     */
    private BigDecimal cuMoney;

}
