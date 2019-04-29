package com.zzlbe.web.controller;

import com.alibaba.fastjson.JSON;
import com.zzlbe.core.business.OrderBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.util.MD5Utils;
import com.zzlbe.dao.entity.GoodsEntity;
import com.zzlbe.dao.entity.SellerEntity;
import com.zzlbe.dao.mapper.*;
import com.zzlbe.dao.search.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    GoodsMapper goodsMapper;

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

            mav.addObject("usernum",usernum);
            mav.addObject("adminernum",adminernum);
            mav.addObject("suggestnum1",suggestnum1);
            mav.addObject("suggestnum2",suggestnum2);
            mav.addObject("getTotalAmountFormat",getTotalAmountFormat());

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

                        mav.addObject("amountSearch",amountSearch);
                        mav.addObject("usernum",userMapper.userCount());
                        mav.addObject("adminernum",sellerMapper.adminerCount());
                        mav.addObject("suggestnum1",suggestMapper.suggestionUndeal1());
                        mav.addObject("suggestionUndeal2",suggestMapper.suggestionUndeal2());
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


    //返回echart所需的格式
    @GetMapping(value="getTotalAmountFormat")
    @ResponseBody
    public String getTotalAmountFormat(){
        List<AmountSearch> amountSearchs=orderMapper.getTotalAmountByMonth();
        String str = JSON.toJSONString(amountSearchs);
        return str;
    }


    @PostMapping("getTotalAmountByMonth")
    public GenericResponse getTotalAmountByMonth() {
        List<AmountSearch> amountSearchs=orderMapper.getTotalAmountByMonth();
        return new GenericResponse<>(amountSearchs);
    }

    @RequestMapping("getTotalAmount")//当前月销售额
    public GenericResponse getTotalAmount() {
        List<AmountSearch> amountSearchs=orderMapper.getTotalAmountByMonth();
        AmountSearch amountSearch=amountSearchs.get(amountSearchs.size()-1);
        return new GenericResponse<>(amountSearch);
    }

    @RequestMapping("/test")
    public String test() {
        return "admin/test";
    }


    @RequestMapping("salesvolume")//当前月销售额
    public ModelAndView salesvolume() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/ch_month.html");
        return  mv;
    }

    @RequestMapping("selectAllGoods")//josn,选择农化产品
    @ResponseBody
    public String selectAllGoods() {
        List<GoodsEntity> goodsEntities= goodsMapper.selectAll();
        String str=JSON.toJSONString(goodsEntities);
        return str;
    }

    @RequestMapping("getGoodSellTop10")//josn,商品6月销售量前10
    @ResponseBody
    public String getGoodSellTop10() {
        List<Goodsselltop10Search> goodsselltop10Searches= orderMapper.getGoodSellTop10();
        String str=JSON.toJSONString(goodsselltop10Searches);
        return str;
    }

    @RequestMapping("getGoodSell")//json,商品6个月销售量变化
    @ResponseBody
    public String getGoodSell(@RequestParam("goodsid") long goodsid) {
        System.out.println("getGoodSell===goodsid:"+goodsid);
        if(goodsid==0){
            List<Goodsselltop10Search> goodsselltop10Searches= orderMapper.getGoodSellTop10();
            goodsid=goodsselltop10Searches.get(0).getId();
        }
        List<GoodssellSearch> goodssellSearches= orderMapper.getGoodSell(goodsid);
        String str=JSON.toJSONString(goodssellSearches);
        return str;
    }

    @RequestMapping("sellersvolume")//当前月销售额
    public ModelAndView sellersvolume() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/ch_person.html");
        return  mv;
    }


    @RequestMapping("getSellerTop10")//josn,销售员6月销售量前10
    @ResponseBody
    public String getSellerTop10() {
        List<Sellertop10Search> Sellertop10Searches= orderMapper.getSellerTop10();
        String str=JSON.toJSONString(Sellertop10Searches);
        return str;
    }


    @RequestMapping("getSellerAll")//josn,选择农化产品
    @ResponseBody
    public String getSellerAll() {
        List<SellersellSearch> SellersellSearches= sellerMapper.getSellerAll();
        String str=JSON.toJSONString(SellersellSearches);
        return str;
    }
}
