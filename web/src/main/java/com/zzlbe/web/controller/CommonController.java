package com.zzlbe.web.controller;

import com.zzlbe.core.util.IpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * PROJECT: Sales
 * DESCRIPTION: 工具类
 *
 * @author duGraceful
 * @date 2019/3/26
 */
@Controller
public class CommonController {

    @GetMapping("ip")
    public String ip(HttpServletRequest request) {
        return IpUtil.getIpAddress(request);
    }

}
