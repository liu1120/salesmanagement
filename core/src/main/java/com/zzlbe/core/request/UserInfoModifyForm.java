package com.zzlbe.core.request;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * PROJECT: Sales
 * DESCRIPTION: 用户个人信息修改表单
 *
 * @author amos
 * @date 2019/4/21
 */
@Data
@Accessors(chain = true)
public class UserInfoModifyForm {

    @NotNull(message = "用户编号不能为空")
    private Long id;

    @NotBlank(message = "名字不能为空")
    @Length(min = 4, max = 20, message = "名字长度不小于4位哟")
    private String name;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    private String wechat;
    private String realname;
    private String address;

}
