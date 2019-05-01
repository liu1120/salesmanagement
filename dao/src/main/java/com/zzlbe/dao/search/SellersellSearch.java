package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class SellersellSearch extends BasePageRequest {


    /**
     * id
     */
    private Long id;
    /**
     * 真实姓名
     */
    private String name;

}
