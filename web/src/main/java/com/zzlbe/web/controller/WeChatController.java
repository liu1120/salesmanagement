package com.zzlbe.web.controller;

import com.zzlbe.core.common.GenericResponse;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PROJECT: Sales
 * DESCRIPTION: TODO
 *
 * @author amos
 * @date 2019/4/22
 */
@RestController
@RequestMapping("wx")
public class WeChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatController.class);

    @GetMapping("login")
    public GenericResponse login(WxLogin wxLogin) {
        LOGGER.info("微信登录 >>>>> code: {}", wxLogin.getCode());
        return GenericResponse.SUCCESS;
    }

    @Data
    private class WxLogin {
        private String code;
    }

}
