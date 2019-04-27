package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * PROJECT: Sales
 * DESCRIPTION: 订单表
 *
 * @author duGraceful
 * @date 2019/4/13
 */
@Data
@Accessors(chain = true)
public class OrderEntity {

    /**
     * 订单编号
     */
    private Long orId;
    /**
     * 农化产品id
     */
    private Long orGoodsId;
    /**
     * 商品数量
     */
    private Integer orCount;
    /**
     * 商品单价
     */
    private BigDecimal orPrice;
    /**
     * 0正常，满300减20：九折。。。
     */
    private String orSay;
    /**
     * 交易总金额
     */
    private BigDecimal orTotalAmount;
    /**
     * 交易时间
     */
    private Date orDatetime;
    /**
     * 销售员编号
     */
    private Long orSellerId;
    /**
     * 用户编号
     */
    private Long orUserId;
    /**
     * 下单审核状态：0待审核，1审核通过，2审核拒绝
     */
    private Integer orType;
    /**
     * 物流单号（暂时不用）
     */
    private Long orLogistics;
    /**
     * 收货地址，json格式{ "province_name":"河南省" , "county_name":"郑州市"  , "town_name":"中原区"  , "village_name":"绿东村街道"  , "address_info":"中原中路146号" }
     */
    private Integer orAddress;
    /**
     * 订单状态：0未付款,1已付款，2待发货,3已发货,4已签收,5退货中,6已退货，7完成交易
     */
    private Integer orStatus;
    /**
     * 下单留言
     */
    private String orWords;
    /**
     * 拒绝理由
     */
    private String orRefuse;

}
