package com.zzlbe.web.controller;

import com.zzlbe.core.util.MD5Utils;
import com.zzlbe.dao.entity.SellerEntity;
import com.zzlbe.dao.mapper.SellerMapper;
import com.zzlbe.dao.mapper.UserMapper;
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
    @Autowired
    UserMapper userMapper;
    //展示登录界面
    @RequestMapping("/login")
    public String login() {
        return "admin/z_login";
    }

    /**
     * 管理员端登录功能 成功返回主页面，失败返回登录界面
     * session 静态资源
     */
    @PostMapping(value="index")
    public ModelAndView index(@RequestParam("login_username") String uname,@RequestParam("username") String username, @RequestParam("password") String password) {
        ModelAndView mav = new ModelAndView();
        if(uname!=null&&uname!=""){//本地已经登陆
            mav.setViewName("admin/index");
        }else{
            MD5Utils md5=new MD5Utils();
            password=md5.getMD5(password);
            if (StringUtils.isAnyBlank(username,password)) {
                mav.addObject("errorinfo", "请输入用户名或密码：");
            }else {
                SellerEntity sellerEntity = sellerMapper.selectSeller(username);
                if(sellerEntity !=null){
                    if(sellerEntity.getPassword().equals(password)&&sellerEntity.getRank()==1){
                        mav.setViewName("admin/index");
                        mav.addObject("login_username", username);
                    } else {
                        mav.addObject("errorinfo", "请输入正确的用户名或密码！");
                    }
                }else {
                    mav.addObject("errorinfo", "用户不存在！");
                }
            }
            mav.setViewName("admin/z_login");
        }
        int usernum=userMapper.userCount();
        mav.addObject("usernum",usernum);
        return mav;
    }


    @RequestMapping("/test")
    public String test() {
        return "admin/test";
    }

}
