package com.zzlbe.web.controller;

import com.zzlbe.core.util.IpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * PROJECT: Sales
 * DESCRIPTION: note
 *
 * @author duGraceful
 * @date 2019/3/26
 */
@Controller
public class HelloController {

    @RequestMapping("/temp")
    public String temp() {
        return "temp";
    }

    /**
     * 展示ExceptionAdvice作用
     *
     * @return null
     * @see com.zzlbe.web.advice.ExceptionAdvice
     */
    @GetMapping("terror")
    public String error() {
        throw new RuntimeException("Error Request!");
    }

    @GetMapping("ip")
    public String ip(HttpServletRequest request) {
        return IpUtil.getIpAddress(request);
    }

}
