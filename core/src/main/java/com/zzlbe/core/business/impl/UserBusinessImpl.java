package com.zzlbe.core.business.impl;

import com.zzlbe.core.UserInfoDTO;
import com.zzlbe.core.business.UserBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.LoginForm;
import com.zzlbe.core.request.RegisterForm;
import com.zzlbe.core.response.UserInfoVO;
import com.zzlbe.core.util.CheckUtil;
import com.zzlbe.core.util.UserUtils;
import com.zzlbe.dao.entity.UserEntity;
import com.zzlbe.dao.mapper.UserMapper;
import com.zzlbe.dao.page.PageResponse;
import com.zzlbe.dao.search.UserSearch;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component("userBusiness")
public class UserBusinessImpl implements UserBusiness {

    @Resource
    private UserMapper userMapper;

    @Override
    public GenericResponse register(RegisterForm registerForm) {
        if (!CheckUtil.isPhoneNo(registerForm.getPhone())) {
            return new GenericResponse("2001", "手机号格式错误!");
        }

        UserEntity userEntity = userMapper.selectByPhoneNo(registerForm.getPhone());
        if (userEntity != null) {
            return new GenericResponse("2002", "该手机号已注册");
        }

        userEntity = new UserEntity();
        BeanUtils.copyProperties(registerForm, userEntity);
        userMapper.insert(userEntity);

        return new GenericResponse<>(generateUserInfo(userEntity));
    }

    @Override
    public GenericResponse login(LoginForm loginForm) {
        if (!CheckUtil.isPhoneNo(loginForm.getPhone())) {
            return new GenericResponse("2001", "手机号格式错误!");
        }

        UserEntity userEntity = userMapper.selectByPhoneNo(loginForm.getPhone());
        if (userEntity == null) {
            return new GenericResponse("2003", "用户未注册");
        }
        if (!userEntity.getPassword().equals(loginForm.getPassword())) {
            return new GenericResponse("2004", "密码错误");
        }

        return new GenericResponse<>(generateUserInfo(userEntity));
    }

    @Override
    public GenericResponse userInfo(UserInfoDTO userInfoDTO) {
        UserEntity userEntity = null;
        if (userInfoDTO.getId() != null) {
            userEntity = userMapper.selectById(userInfoDTO.getId());
        } else if (userInfoDTO.getPhoneNo() != null) {
            userEntity = userMapper.selectByPhoneNo(userInfoDTO.getPhoneNo());
        } else if (userInfoDTO.getToken() != null) {
            userEntity = userMapper.selectById(UserUtils.getUserId(userInfoDTO.getToken()));
        }

        if (userEntity == null) {
            return new GenericResponse("2003", "手机号未注册!");
        }

        return new GenericResponse<>(generateUserInfo(userEntity));
    }

    @Override
    public GenericResponse userAll(UserSearch userSearch) {
        List<UserInfoVO> userInfoVOS = new ArrayList<>();
        List<UserEntity> userEntities = userMapper.selectByPage(userSearch);
        Integer total = userMapper.selectByPageTotal(userSearch);
        if (userEntities != null) {
            userEntities.forEach(userEntity -> userInfoVOS.add(generateUserInfo(userEntity)));
        }
        PageResponse<UserInfoVO> pageResponse = new PageResponse<>();
        pageResponse.setSize(userInfoVOS.size());
        pageResponse.setList(userInfoVOS);
        pageResponse.setTotal(total);

        return new GenericResponse<>(pageResponse);
    }

    /**
     * 构造用户信息
     *
     * @param userEntity UserEntity
     * @return UserInfoVO
     */
    private UserInfoVO generateUserInfo(UserEntity userEntity) {
        String token = UserUtils.getUserToken(userEntity.getId());

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setToken(token);
        BeanUtils.copyProperties(userEntity, userInfoVO);

        return userInfoVO;
    }
}
