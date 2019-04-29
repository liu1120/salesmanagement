package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class Sellertop10Search extends BasePageRequest {


    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 总交易量
     */
    private double count;

}
