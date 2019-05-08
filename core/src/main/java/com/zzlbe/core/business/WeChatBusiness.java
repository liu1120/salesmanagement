package com.zzlbe.core.business;

import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.dto.wx.WxLoginRequest;

/**
 * PROJECT: Sales
 * DESCRIPTION: TODO
 *
 * @author amos
 * @date 2019/5/8
 */
public interface WeChatBusiness {

    /**
     * 微信登录
     *
     * @param wxLoginRequest 微信登录信息
     * @return GenericResponse
     */
    GenericResponse weChatLogin(WxLoginRequest wxLoginRequest);

    /**
     * 完善用户信息
     *
     * @param wxLoginRequest 用户信息
     * @return GenericResponse
     */
    GenericResponse modifyUserInfo(WxLoginRequest wxLoginRequest);

}
