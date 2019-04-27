package com.zzlbe.web.controller;

import com.zzlbe.core.business.OrderBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.util.MD5Utils;
import com.zzlbe.dao.entity.SellerEntity;
import com.zzlbe.dao.mapper.OrderMapper;
import com.zzlbe.dao.mapper.SellerMapper;
import com.zzlbe.dao.mapper.SuggestMapper;
import com.zzlbe.dao.mapper.UserMapper;
import com.zzlbe.dao.search.AmountSearch;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    SellerMapper sellerMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SuggestMapper suggestMapper;
    @Autowired
    OrderBusiness orderBusiness;
    @Autowired
    OrderMapper orderMapper;
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
            System.out.println("本地已经登陆:"+uname);
            mav.addObject("login_username",uname);
            int usernum=userMapper.userCount();//用户总人数
            System.out.println("用户总人数:"+usernum);
            int adminernum=sellerMapper.adminerCount();//销售员总数
            int suggestnum1=suggestMapper.suggestionUndeal1();//未处理好意见
            int suggestnum2=suggestMapper.suggestionUndeal2();//未处理意见

            List<AmountSearch> amountSearchs=orderMapper.getTotalAmountByMonth();
            AmountSearch amountSearch=amountSearchs.get(amountSearchs.size()-1);
            mav.addObject("amountSearch",amountSearch);
            mav.addObject("amountSearchs",amountSearchs);

            mav.addObject("usernum",usernum);
            mav.addObject("adminernum",adminernum);
            mav.addObject("suggestnum1",suggestnum1);
            mav.addObject("suggestnum2",suggestnum2);
            mav.addObject("getTotalAmountByMonth",getTotalAmountByMonth());
            mav.addObject("getTotalAmount",getTotalAmount());

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
                        List<AmountSearch> amountSearchs=orderMapper.getTotalAmountByMonth();
                        AmountSearch amountSearch=amountSearchs.get(amountSearchs.size()-1);

                        mav.addObject("amountSearchs",amountSearchs);
                        mav.addObject("amountSearch",amountSearch);
                        mav.addObject("usernum",userMapper.userCount());
                        mav.addObject("adminernum",sellerMapper.adminerCount());
                        mav.addObject("suggestnum1",suggestMapper.suggestionUndeal1());
                        mav.addObject("suggestionUndeal2",suggestMapper.suggestionUndeal2());
                        mav.addObject("getTotalAmountByMonth",getTotalAmountByMonth());
                        mav.addObject("getTotalAmountByMonth",getTotalAmount());
                        mav.addObject("login_username", username);
                        mav.setViewName("admin/index");
                        //queryIndex();

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
        return mav;
    }

    @RequestMapping(value="index")
    public String index() {
        String uname=null,username=null,password=null;
//        index(uname,username,password);
            return "admin/z_login";
    }


    @RequestMapping("getTotalAmountByMonth")
    public GenericResponse getTotalAmountByMonth() {
        List<AmountSearch> amountSearchs=orderMapper.getTotalAmountByMonth();
        return new GenericResponse<>(amountSearchs);
    }
    @RequestMapping("getTotalAmount")
    public GenericResponse getTotalAmount() {
        List<AmountSearch> amountSearchs=orderMapper.getTotalAmountByMonth();
        AmountSearch amountSearch=amountSearchs.get(amountSearchs.size()-1);
        return new GenericResponse<>(amountSearch);
    }

    @RequestMapping("/test1")
    public void test1() {
        orderBusiness.getTotalAmount();
    }

    @RequestMapping("/test")
    public String test() {
        return "admin/test";
    }

}
