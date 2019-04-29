package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class GoodssellSearch extends BasePageRequest {


    /**
     * 月份
     */
    private String month;
    /**
     * 总交易额
     */
    private double count;

}
