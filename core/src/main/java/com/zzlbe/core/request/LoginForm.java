package com.zzlbe.core.request;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * PROJECT: Sales
 * DESCRIPTION: 登录接口
 *
 * @author duGraceful
 * @date 2019/3/26
 */
@Data
@Accessors(chain = true)
public class LoginForm {

    private String name;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 18, message = "密码长度不小于6位哟")
    private String password;

}
