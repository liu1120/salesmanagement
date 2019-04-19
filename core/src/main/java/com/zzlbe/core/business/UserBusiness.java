package com.zzlbe.core.business;

import com.zzlbe.core.UserInfoDTO;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.LoginForm;
import com.zzlbe.core.request.RegisterForm;
import com.zzlbe.dao.search.UserSearch;

public interface UserBusiness {

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
