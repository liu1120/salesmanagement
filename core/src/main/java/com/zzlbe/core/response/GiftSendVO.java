package com.zzlbe.core.response;

import lombok.Data;

import java.util.Date;

/**
 * PROJECT: Sales
 * DESCRIPTION: 已发放礼品信息
 *
 * @author amos
 * @date 2019/5/1
 */
@Data
public class GiftSendVO {

    private Long id;
    /**
     * 礼品编号
     */
    private Long giftId;
    /**
     * 销售员id
     */
    private Long fromid;
    /**
     * 用户id
     */
    private Long toid;
    /**
     * 用户手机号
     */
    private String tophone;
    /**
     * 用户地址
     */
    private String address;
    /**
     * 0销售员派送，1物流派送。默认0
     */
    private Integer type;
    /**
     * 物流单号（暂时不用）
     */
    private Long logistics;
    /**
     * 礼品数量
     */
    private Long num;
    /**
     * 花费积分
     */
    private Long credit;
    /**
     * 0代发货，1待收货，2已签收
     */
    private Integer status;
    /**
     * 发放日期
     */
    private Date date;

    /**
     * 名称/图片
     */
    private String giftName;
    private String giftImage;

}
