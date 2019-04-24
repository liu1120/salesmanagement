package com.zzlbe.core.dto.wx;

import lombok.Data;

/**
 * PROJECT: sales management
 * DESCRIPTION: 微信小程序端登录Request
 *
 * @author amos.wang
 * @date 2019/4/23
 */
@Data
public class WxLoginRequest {
    private String code;
    private String phoneNo;
    private String password;
    private String username;
    private String iconUrl;
    private String address;
}
