package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class UserSearch extends BasePageRequest {

    private String name;
    private String wechat;
    private String phone;
    private String realname;

}
