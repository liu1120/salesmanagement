package com.zzlbe.core.business.impl;

import com.alibaba.fastjson.JSON;
import com.zzlbe.core.business.WeChatBusiness;
import com.zzlbe.core.common.ErrorCodeEnum;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.dto.wx.Code2SessionResponse;
import com.zzlbe.core.dto.wx.WxErrorCodeEnum;
import com.zzlbe.core.dto.wx.WxLoginRequest;
import com.zzlbe.core.util.CheckUtil;
import com.zzlbe.dao.entity.UserEntity;
import com.zzlbe.dao.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * PROJECT: Sales
 * DESCRIPTION: TODO
 *
 * @author amos
 * @date 2019/5/8
 */
@Component("weChatBusiness")
public class WeChatBusinessImpl extends BaseBusinessImpl implements WeChatBusiness {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBusinessImpl.class);

    @Resource
    private UserMapper userMapper;
    @Resource
    private RestTemplate restTemplate;

    private static final String APP_KEY = "wxc60802b4db0c011d";
    private static final String SECRET = "c57a91fd6287fca522b754009b633785";

    @Override
    public GenericResponse weChatLogin(WxLoginRequest wxLoginRequest) {
        // 通过js_code请求微信获取openid/sessionKey等信息
        String wxCode2Session = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                String.format(wxCode2Session, APP_KEY, SECRET, wxLoginRequest.getCode()), String.class);
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
    public GenericResponse modifyUserInfo(WxLoginRequest wxLoginRequest) {
        Long userId = wxLoginRequest.getUserId();
        if (userId == null) {
            return new GenericResponse(ErrorCodeEnum.USER_NOT_FOUND);
        }

        UserEntity userEntity = userMapper.selectById(userId);
        if (userEntity == null) {
            return new GenericResponse(ErrorCodeEnum.USER_NOT_FOUND);
        }

        userEntity.setName(StringUtils.isBlank(wxLoginRequest.getUsername()) ? null : wxLoginRequest.getUsername().trim());

        // 校验手机号
        String phoneNo = wxLoginRequest.getPhoneNo();
        if (StringUtils.isNotBlank(phoneNo)) {
            if (!CheckUtil.isPhoneNo(phoneNo)) {
                return new GenericResponse(ErrorCodeEnum.USER_PHONE_FORMAT_ERROR);
            }
            userEntity.setPhone(phoneNo);
        }

        userEntity.setPassword(StringUtils.isBlank(wxLoginRequest.getPassword()) ? null : wxLoginRequest.getPassword().trim());

        userMapper.update(userEntity);

        return userInfoResponse(userEntity);
    }
}
