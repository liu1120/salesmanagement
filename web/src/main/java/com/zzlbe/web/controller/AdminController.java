package com.zzlbe.web.controller;

import com.zzlbe.core.util.MD5Utils;
import com.zzlbe.dao.entity.SellerEntity;
import com.zzlbe.dao.mapper.SellerMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    SellerMapper sellerMapper;

    //展示登录界面
    @RequestMapping("/login")
    public String login() {
        return "admin/z_login";
    }

    /**
     * 管理员端登录功能 成功返回主页面，失败返回登录界面
     * session 静态资源
     */
    @PostMapping(value="checkLogin")
    public ModelAndView checkLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        MD5Utils md5=new MD5Utils();
        password=md5.getMD5(password);
        if (StringUtils.isAnyBlank(username,password)) {
            return new ModelAndView("admin/z_login");
        }else {
            if(sellerMapper.selectSeller(username)!=null){
                SellerEntity sellerEntity = sellerMapper.selectSeller(username);
                if(sellerEntity.getPassword().equals(password)&&sellerEntity.getRank()==1){
                    System.out.println("登录成功"+sellerEntity.getPassword());
                    return new ModelAndView("/admin/index");
                }
            }
        }
        return new ModelAndView("/admin/z_login");
    }


}
