package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class SellerEntity {
    private Long id;
    @NotBlank(message = "用户名不能为空")
    private String name;
    private String password;
    private String img;
    private String wechat;
    @NotBlank(message = "手机号不能为空")

    private String phone;
    @NotBlank(message = "姓名不能为空")
    private String realname;

    private Integer rank;
    @NotBlank(message = "部门不能为空")
    private String post;
    @NotBlank(message = "职位不能为空")
    private String position;
    private Long number;
}
