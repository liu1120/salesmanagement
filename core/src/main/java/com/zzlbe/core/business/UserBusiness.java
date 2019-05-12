package com.zzlbe.core.business;

import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.dto.UserInfoDTO;
import com.zzlbe.core.request.LoginForm;
import com.zzlbe.core.request.RegisterForm;
import com.zzlbe.core.request.UserInfoModifyForm;
import com.zzlbe.core.request.UserPasswordModifyForm;
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

    /**
     * 获取积分记录（暂仅支持查询订单中获取的积分，积分业务复杂的话，需增加积分变动记录表）
     *
     * @param userId 用户编号
     * @return GenericResponse
     */
    GenericResponse creditGet(Long userId);

    /**
     * 积分消费记录（暂仅支持查询礼品中消费的积分，积分业务复杂的话，需增加积分变动记录表）
     *
     * @param userId 用户编号
     * @return GenericResponse
     */
    GenericResponse creditConsume(Long userId);

    /**
     * 用户积分异常, 修复用户积分
     *
     * @param userId 用户编号
     * @return GenericResponse
     */
    GenericResponse fixCredit(Long userId);
}
