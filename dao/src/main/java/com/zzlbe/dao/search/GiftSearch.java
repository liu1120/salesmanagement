package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 礼品Search
 *
 * @author amos
 * @date 2019/5/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GiftSearch extends BasePageRequest {

    /**
     * 礼品名称
     */
    private String giftName;
    /**
     * 礼品消耗积分
     */
    private Integer credit;


    /**
     * 礼品编号
     */
    private Long giftId;
    private List<Long> giftIds;
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
}
