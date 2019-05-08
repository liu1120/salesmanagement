package com.zzlbe.core.business.impl;

import com.zzlbe.core.business.UserBusiness;
import com.zzlbe.core.common.ErrorCodeEnum;
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
public class UserBusinessImpl extends BaseBusinessImpl implements UserBusiness {

    @Resource
    private UserMapper userMapper;

    @Override
    public GenericResponse register(RegisterForm registerForm) {
        if (!CheckUtil.isPhoneNo(registerForm.getPhone())) {
            return new GenericResponse(ErrorCodeEnum.USER_PHONE_FORMAT_ERROR);
        }

        UserEntity userEntity = userMapper.selectByPhoneNo(registerForm.getPhone());
        if (userEntity != null) {
            return new GenericResponse(ErrorCodeEnum.USER_PHONE_HAS_REGISTERED);
        }

        userEntity = new UserEntity();
        BeanUtils.copyProperties(registerForm, userEntity);
        userMapper.insert(userEntity);

        return userInfoResponse(userEntity);
    }

    @Override
    public GenericResponse login(LoginForm loginForm) {
        if (!CheckUtil.isPhoneNo(loginForm.getPhone())) {
            return new GenericResponse(ErrorCodeEnum.USER_PHONE_FORMAT_ERROR);
        }

        UserEntity userEntity = userMapper.selectByPhoneNo(loginForm.getPhone());
        if (userEntity == null) {
            return new GenericResponse(ErrorCodeEnum.USER_NOT_FOUND);
        }
        if (!userEntity.getPassword().equals(loginForm.getPassword())) {
            return new GenericResponse(ErrorCodeEnum.USER_LOGIN_PASSWORD_ERROR);
        }

        return userInfoResponse(userEntity);
    }

    @Override
    public GenericResponse modify(UserInfoModifyForm modifyForm) {
        if (!CheckUtil.isPhoneNo(modifyForm.getPhone())) {
            return new GenericResponse(ErrorCodeEnum.USER_PHONE_FORMAT_ERROR);
        }

        UserEntity userEntity = userMapper.selectByPhoneNo(modifyForm.getPhone());
        if (userEntity != null && !userEntity.getId().equals(modifyForm.getId())) {
            return new GenericResponse(ErrorCodeEnum.USER_MODIFY_PHONE_HAS_REGISTERED);
        }

        userEntity = userMapper.selectById(modifyForm.getId());
        if (userEntity == null) {
            return new GenericResponse(ErrorCodeEnum.USER_NOT_FOUND);
        }

        BeanUtils.copyProperties(modifyForm, userEntity);
        userMapper.update(userEntity);

        return userInfoResponse(userEntity);
    }

    @Override
    public GenericResponse passwordModify(UserPasswordModifyForm modifyForm) {
        if (!modifyForm.getNewPassword().equals(modifyForm.getEnterPassword())) {
            return new GenericResponse(ErrorCodeEnum.USER_MODIFY_PASSWORD_ENTER_ERROR);
        }
        UserEntity userEntity = userMapper.selectById(modifyForm.getId());
        if (userEntity == null) {
            return new GenericResponse(ErrorCodeEnum.USER_NOT_FOUND);
        }
        if (!userEntity.getPassword().equals(modifyForm.getOldPassword())) {
            return new GenericResponse(ErrorCodeEnum.USER_MODIFY_PASSWORD_ERROR);
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
            return new GenericResponse(ErrorCodeEnum.USER_NOT_FOUND);
        }

        return userInfoResponse(userEntity);
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

}
