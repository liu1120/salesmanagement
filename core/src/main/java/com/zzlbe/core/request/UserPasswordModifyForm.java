package com.zzlbe.core.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * PROJECT: Sales
 * DESCRIPTION: 修改密码表单
 *
 * @author amos
 * @date 2019/4/21
 */
@Data
public class UserPasswordModifyForm {

    @NotNull(message = "用户编号不能为空")
    private Long id;

    @NotBlank(message = "原始密码不能为空")
    @Length(min = 6, max = 18, message = "原始密码长度不小于6位哟")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Length(min = 6, max = 18, message = "新密码长度不小于6位哟")
    @NotEmpty
    private String newPassword;

    @NotBlank(message = "确认密码不能为空")
    @Length(min = 6, max = 18, message = "确认密码长度不小于6位哟")
    private String enterPassword;

}
