package com.zzlbe.core.business;

import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.dto.UserInfoDTO;
import com.zzlbe.core.dto.wx.WxLoginRequest;
import com.zzlbe.core.request.LoginForm;
import com.zzlbe.core.request.RegisterForm;
import com.zzlbe.core.request.UserInfoModifyForm;
import com.zzlbe.core.request.UserPasswordModifyForm;
import com.zzlbe.dao.search.UserSearch;

public interface UserBusiness {

    /**
     * 微信登录
     *
     * @param wxLoginRequest 微信登录信息
     * @return GenericResponse
     */
    GenericResponse weChatLogin(WxLoginRequest wxLoginRequest);

    /**
     * 用户登录
     *
     * @param loginForm LoginForm
     * @return UserInfoVO
     */
    GenericResponse login(LoginForm loginForm);

    /**
     * 用户注册
     *
     * @param registerForm RegisterForm
     * @return UserInfoVO
     */
    GenericResponse register(RegisterForm registerForm);

    /**
     * 修改用户个人信息
     *
     * @param modifyForm UserInfoModifyForm
     * @return UserInfoVO
     */
    GenericResponse modify(UserInfoModifyForm modifyForm);

    /**
     * 修改用户密码
     *
     * @param modifyForm UserPasswordModifyForm
     * @return UserInfoVO
     */
    GenericResponse passwordModify(UserPasswordModifyForm modifyForm);

    /**
     * 获取用户信息
     *
     * @param userInfoDTO 前置信息
     * @return UserInfoVO
     */
    GenericResponse userInfo(UserInfoDTO userInfoDTO);

    /**
     * 获取用户信息
     *
     * @param userSearch 查询信息
     * @return List<UserInfoVO>
     */
    GenericResponse userAll(UserSearch userSearch);

}
