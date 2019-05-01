package com.zzlbe.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class ActivityController {

    //促销活动  ---后续实现
    @GetMapping("activity")//当前地区销售额
    public ModelAndView activity() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/ac_list.html");
        return  mv;
    }

}
