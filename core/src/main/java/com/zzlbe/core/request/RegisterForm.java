package com.zzlbe.core.request;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class RegisterForm {

    @NotBlank(message = "名字不能为空")
    @Length(min = 4, max = 20, message = "名字长度不小于4位哟")
    private String name;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 18, message = "密码长度不小于6位哟")
    private String password;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    private String wechat;
    private String realname;
    private String address;

}
