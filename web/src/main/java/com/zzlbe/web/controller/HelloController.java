package com.zzlbe.web.controller;

import com.zzlbe.core.util.IpUtil;
import com.zzlbe.dao.entity.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
        List<UserEntity> learnList =new ArrayList<UserEntity>();
        UserEntity bean =new UserEntity();
        learnList.add(bean);

        bean =new UserEntity();
        learnList.add(bean);

        ModelAndView modelAndView = new ModelAndView("admin/z_login");
        modelAndView.addObject("learnList", learnList);
        return "admin/z_login";
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
