package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class SentgiftSearch extends BasePageRequest {

    private Long id;
    /**
     * 礼品编号
     */
    private Long giftId;
    private String gname;
    /**
     * 销售员id
     */
    private Long fromid;
    /**
     * 用户id
     */
    private Long toid;
    private String uname;
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
    private String date;

}
