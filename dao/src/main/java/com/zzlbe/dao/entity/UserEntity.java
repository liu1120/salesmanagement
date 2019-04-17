package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserEntity {

    private Long id;
    private String name;
    private String password;
    private String wechat;
    private String phone;
    private String realname;
    private String address;
    private Long credit;

}
