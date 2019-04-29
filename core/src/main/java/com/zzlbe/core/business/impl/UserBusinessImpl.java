package com.zzlbe.core.business.impl;

import com.alibaba.fastjson.JSON;
import com.zzlbe.core.business.UserBusiness;
import com.zzlbe.core.common.ErrorCodeEnum;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.dto.UserInfoDTO;
import com.zzlbe.core.dto.wx.Code2SessionResponse;
import com.zzlbe.core.dto.wx.WxErrorCodeEnum;
import com.zzlbe.core.dto.wx.WxLoginRequest;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component("userBusiness")
public class UserBusinessImpl implements UserBusiness {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBusinessImpl.class);

    @Resource
    private UserMapper userMapper;
    @Resource
    private RestTemplate restTemplate;


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
    public GenericResponse weChatLogin(WxLoginRequest wxLoginRequest) {
        // 通过js_code请求微信获取openid/sessionKey等信息
        String appKey = "wxc60802b4db0c011d";
        String secret = "c57a91fd6287fca522b754009b633785";
        String wxCode2Session = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                String.format(wxCode2Session, appKey, secret, wxLoginRequest.getCode()), String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseDataStr = responseEntity.getBody();
            LOGGER.info("微信登录 >>>>> response data str: {}", responseDataStr);

            Code2SessionResponse code2SessionResponse = JSON.parseObject(responseDataStr, Code2SessionResponse.class);
            if (code2SessionResponse == null) {
                return GenericResponse.ERROR;
            }
            // 获取 session 失败执行的操作
            WxErrorCodeEnum errorCodeEnum = WxErrorCodeEnum.getValue(code2SessionResponse.getErrcode());
            if (errorCodeEnum != null && !WxErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return GenericResponse.FAIL.setMessage(code2SessionResponse.getErrmsg());
            }

            // 用户唯一标示
            String openid = code2SessionResponse.getOpenid();
            // 会话密钥
            String sessionKey = code2SessionResponse.getSessionKey();
            // 用户在开放平台的唯一标识符
            String unionId = code2SessionResponse.getUnionid();

            LOGGER.info("微信登录 >>>>> openid: {}, unionId: {}", openid, unionId);

            UserEntity userEntity = userMapper.selectByOpenid(openid);
            if (userEntity == null) {
                userEntity = new UserEntity();
                userEntity.setOpenid(openid);
                userEntity.setSessionKey(sessionKey);
                userEntity.setName(wxLoginRequest.getUsername());
                userEntity.setPhone(wxLoginRequest.getPhoneNo());
                userEntity.setPassword(wxLoginRequest.getPassword());
                userMapper.insert(userEntity);
            } else {
                userEntity.setSessionKey(sessionKey);
                userEntity.setName(wxLoginRequest.getUsername());
                userEntity.setPhone(wxLoginRequest.getPhoneNo());
                userEntity.setPassword(wxLoginRequest.getPassword());
                userMapper.update(userEntity);
            }

            return userInfoResponse(userEntity);
        }

        return GenericResponse.ERROR;
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

    private GenericResponse<UserInfoVO> userInfoResponse(UserEntity userEntity) {
        return new GenericResponse<>(generateUserInfo(userEntity));
    }

}
