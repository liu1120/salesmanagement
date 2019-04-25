package com.zzlbe.web.controller;

import com.zzlbe.core.util.MD5Utils;
import com.zzlbe.dao.entity.SellerEntity;
import com.zzlbe.dao.mapper.SellerMapper;
import com.zzlbe.dao.mapper.SuggestMapper;
import com.zzlbe.dao.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    @Autowired
    SuggestMapper suggestMapper;
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
        System.out.println("Post");
        if(uname!=null&&uname!=""){//本地已经登陆
            mav.addObject("login_username",uname);
            mav.setViewName("admin/index");
        }else if(uname==null&&username==null&&password==null){
            mav.setViewName("admin/z_login");
        }else{
            if (StringUtils.isAnyBlank(username,password)) {
                mav.addObject("errorinfo", "请输入用户名或密码：");
            }else {
                SellerEntity sellerEntity = sellerMapper.selectSeller(username);
                if(sellerEntity !=null){
                    MD5Utils md5=new MD5Utils();
                    password=md5.getMD5(password);
                    if(sellerEntity.getPassword().equals(password)&&sellerEntity.getRank()==1){
                        mav.setViewName("admin/index");
                        mav.addObject("login_username", username);
                        queryIndex();
                        return mav;
                    } else {
                        mav.addObject("errorinfo", "请输入正确的用户名或密码！");
                    }
                }else {
                    mav.addObject("errorinfo", "用户不存在！");
                }
            }
            mav.setViewName("admin/z_login");
        }
        queryIndex();
        int usernum=userMapper.userCount();
        System.out.println("usernum:"+usernum);
        mav.addObject("usernum",usernum);
        return mav;
    }

    @GetMapping(value="index")
    public void index() {
        String uname=null,username=null,password=null;
        index(uname,username,password);
    }

    public void queryIndex() {
        int usernum=userMapper.userCount();
        System.out.println("usernum:"+usernum);
        int adminernum=sellerMapper.adminerCount();
        System.out.println("adminernum:"+adminernum);
        int suggestnum=suggestMapper.suggestionCount();
        System.out.println("suggestnum:"+suggestnum);
    }

    @RequestMapping("/test")
    public String test() {
        return "admin/test";
    }

    @RequestMapping("/suggestionUndeal")
    public int suggestionUndeal() {

        return suggestMapper.suggestionUndeal();
    }

}
