package com.zzlbe.dao.search;

import lombok.Data;

/**
 * PROJECT: Sales
 * DESCRIPTION: 获取积分明细 By Order
 *
 * @author amos
 * @date 2019/5/12
 */
@Data
public class CreditGetByOrderVO {

    private Integer credit;
    private Integer count;
    private Long totalCredit;

    private String goodsName;
    private String goodsVersion;
    private String goodsIntroduce;
    private String goodsImagePath;

}
