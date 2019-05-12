package com.zzlbe.dao.search;

import lombok.Data;

import java.util.Date;

/**
 * PROJECT: Sales
 * DESCRIPTION: 消费积分明细 By SendGift
 *
 * @author amos
 * @date 2019/5/12
 */
@Data
public class CreditConsumeBySendGiftVO {
    /**
     * 消费积分By获取礼品
     */
    private Integer credit;
    private Integer count;
    private Date date;
    private Integer status;
    private Long totalCredit;
    /**
     * 礼品信息
     */
    private String giftName;
    private String giftDescribe;
    private Integer giftUnitCredit;
    private String giftImgPath;
    /**
     * 收货地址信息
     */
    private String address;
    private String addressInfo;
    private String userName;
    private String userPhone;

}
