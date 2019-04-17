package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SellerEntity {
    private long id;
    private String name;
    private String password;
    private String img;
    private String wechat;
    private String phone;
    private String realname;
    private int rank;
    private String post;
    private String position;
    private long number;
 }
