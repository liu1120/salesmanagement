package com.zzlbe.web.controller;

import com.alibaba.fastjson.JSON;
import com.zzlbe.core.business.UserBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.dto.wx.WxLoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * PROJECT: Sales
 * DESCRIPTION: 微信登录相关
 *
 * @author amos
 * @date 2019/4/22
 */
@RestController
@RequestMapping("wx")
public class WeChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatController.class);

    @Resource
    private UserBusiness userBusiness;

    @GetMapping("login")
    public GenericResponse login(WxLoginRequest wxLoginRequest) {
        LOGGER.info("微信登录 >>>>> message: {}", JSON.toJSONString(wxLoginRequest));

        return userBusiness.weChatLogin(wxLoginRequest);
    }

}
