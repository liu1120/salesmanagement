package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeSearch extends BasePageRequest {
    private Integer id;
    private String title;
    private Integer type;
    private String content;
    private String sp_name;
    private String time;
    private Long num;
}
