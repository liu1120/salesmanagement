package com.zzlbe.core.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * PROJECT: sales
 * DESCRIPTION: 订单相关FORM
 *
 * @author amos
 * @date 2019/4/20
 */
@Data
public class OrderForm {

    /**
     * 判断是用户下单还是销售员下单
     * 用户下单：0;
     * 销售员下单：1
     */
    @NotNull(message = "下单方式不能为空")
    private Boolean orderFlag;

    /**
     * 订单编号
     */
    private Long orId;
    /**
     * 农化产品id
     */
    @NotNull(message = "请选择下单产品")
    private Long orGoodsId;
    /**
     * 商品数量
     */
    @NotNull(message = "商品数量为空")
    @Min(value = 1, message = "商品数量不能少于1")
    private Integer orCount;
    /**
     * 活动ID (正常/打折/满减)
     */
    private Long orSay;
    /**
     * 销售员编号getOrderList
     */
    private Long orSellerId;
    /**
     * 用户编号
     */
    private Long orUserId;
    /**
     * 用户手机号（销售员下单时使用）
     */
    private String phoneNo;
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
    @NotNull(message = "请指定收货地址")
    private Integer orAddress;
    /**
     * 0未付款,1已付款，2待发货,3已发货,4已签收,5退货中,6已退货，7完成交易
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
