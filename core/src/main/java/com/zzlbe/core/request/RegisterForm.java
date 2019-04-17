package com.zzlbe.core.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegisterForm {

    private String name;
    private String password;
    private String wechat;
    private String phone;
    private String realname;
    private String address;

}
