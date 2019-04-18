package com.zzlbe.web.controller;

import com.zzlbe.core.util.IpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * PROJECT: Sales
 * DESCRIPTION: note
 *
 * @author duGraceful
 * @date 2019/3/26
 */
@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello Sales Management!";
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
