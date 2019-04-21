package com.zzlbe.core.business.impl;

import com.zzlbe.core.business.UserBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.dto.UserInfoDTO;
import com.zzlbe.core.request.LoginForm;
import com.zzlbe.core.request.RegisterForm;
import com.zzlbe.core.request.UserInfoModifyForm;
import com.zzlbe.core.request.UserPasswordModifyForm;
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

    private static GenericResponse PHONE_NO_FORMAT_ERROR = new GenericResponse("2001", "手机号格式错误!");
    private static GenericResponse USER_NOT_EXIST = new GenericResponse("2003", "用户不存在!");

    @Override
    public GenericResponse register(RegisterForm registerForm) {
        if (!CheckUtil.isPhoneNo(registerForm.getPhone())) {
            return PHONE_NO_FORMAT_ERROR;
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
            return PHONE_NO_FORMAT_ERROR;
        }

        UserEntity userEntity = userMapper.selectByPhoneNo(loginForm.getPhone());
        if (userEntity == null) {
            return USER_NOT_EXIST;
        }
        if (!userEntity.getPassword().equals(loginForm.getPassword())) {
            return new GenericResponse("2004", "密码错误");
        }

        return new GenericResponse<>(generateUserInfo(userEntity));
    }

    @Override
    public GenericResponse modify(UserInfoModifyForm modifyForm) {
        if (!CheckUtil.isPhoneNo(modifyForm.getPhone())) {
            return PHONE_NO_FORMAT_ERROR;
        }

        UserEntity userEntity = userMapper.selectByPhoneNo(modifyForm.getPhone());
        if (userEntity != null && !userEntity.getId().equals(modifyForm.getId())) {
            return new GenericResponse("2002", modifyForm.getPhone() + " 手机号已占用");
        }

        userEntity = userMapper.selectById(modifyForm.getId());
        if (userEntity == null) {
            return USER_NOT_EXIST;
        }

        BeanUtils.copyProperties(modifyForm, userEntity);
        userMapper.update(userEntity);

        return new GenericResponse<>(generateUserInfo(userEntity));
    }

    @Override
    public GenericResponse passwordModify(UserPasswordModifyForm modifyForm) {
        if (!modifyForm.getNewPassword().equals(modifyForm.getEnterPassword())) {
            return new GenericResponse("2005", "两次输入的不一致!");
        }
        UserEntity userEntity = userMapper.selectById(modifyForm.getId());
        if (userEntity == null) {
            return USER_NOT_EXIST;
        }
        if (!userEntity.getPassword().equals(modifyForm.getOldPassword())) {
            return new GenericResponse("2006", "原始密码错误!");
        }

        userEntity.setPassword(modifyForm.getEnterPassword());
        userMapper.update(userEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse userInfo(UserInfoDTO userInfoDTO) {
        UserEntity userEntity = null;
        if (userInfoDTO.getId() != null) {
            userEntity = userMapper.selectById(userInfoDTO.getId());
        } else if (userInfoDTO.getPhone() != null) {
            userEntity = userMapper.selectByPhoneNo(userInfoDTO.getPhone());
        } else if (userInfoDTO.getToken() != null) {
            userEntity = userMapper.selectById(UserUtils.getUserId(userInfoDTO.getToken()));
        }

        if (userEntity == null) {
            return USER_NOT_EXIST;
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
