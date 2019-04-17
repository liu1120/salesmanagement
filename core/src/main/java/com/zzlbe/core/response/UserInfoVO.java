package com.zzlbe.core.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInfoVO {

    private Long id;
    private String token;
    private String name;
    private String wechat;
    private String phone;
    private String realname;
    private String address;
    private Long credit;

}

