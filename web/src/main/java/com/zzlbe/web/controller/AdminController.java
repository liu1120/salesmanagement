package com.zzlbe.web.controller;

import com.alibaba.fastjson.JSON;
import com.zzlbe.core.business.OrderBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.util.MD5Utils;
import com.zzlbe.dao.entity.GoodsEntity;
import com.zzlbe.dao.entity.SellerEntity;
import com.zzlbe.dao.entity.SuggestEntity;
import com.zzlbe.dao.entity.SuggestTopicEntity;
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
    SuggestTopicMapper suggestTopicMapper;
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
        if(goodsid==0){
            List<Goodsselltop10Search> goodsselltop10Searches= orderMapper.getGoodSellTop10();
            goodsid=goodsselltop10Searches.get(0).getId();
        }
        List<GoodssellSearch> goodssellSearches= orderMapper.getGoodSell(goodsid);
        String str=JSON.toJSONString(goodssellSearches);
        return str;
    }


    @RequestMapping("getSellerSell")//json,销售员6个月销售量变化
    @ResponseBody
    public String getSellerSell(@RequestParam("sellerid") long sellerid) {
        if(sellerid==0){
            List<Sellertop10Search> Sellertop10Search= orderMapper.getSellerTop10();
            sellerid=Sellertop10Search.get(0).getId();
        }
        List<GoodssellSearch> goodssellSearches= orderMapper.getSellerSell(sellerid);
        String str=JSON.toJSONString(goodssellSearches);
        System.out.println("getSellerSell:"+str);
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

    @GetMapping("areavolume")//当前地区销售额
    public ModelAndView areavolume() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/ch_area.html");
        return  mv;
    }

    //促销活动  ---后续实现
    @GetMapping("activity")//当前地区销售额
    public ModelAndView activity() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/ac_list.html");
        return  mv;
    }
    @GetMapping("suggest")//后台查看-投诉与建议
    public ModelAndView suggest() {
        ModelAndView mv=new ModelAndView();

        mv.setViewName("admin/z_suggest.html");
        return  mv;
    }

    @GetMapping("getAllgoods")//后台查看-商品列表
    public ModelAndView getAllgoods(@RequestParam("pageNo") Integer pageNo) {
        GoodsSearch goodsSearch=new GoodsSearch();
        if (pageNo!=null){
            goodsSearch.setPage(pageNo);
        }
        ModelAndView mv=new ModelAndView();
        List<GoodsEntity> goodsEntities=goodsMapper.selectAllByPage(goodsSearch);

        Integer total=goodsMapper.selectTotal();
        int page=goodsSearch.getPage();//当前页
        int size=goodsSearch.getSize();//页码大小

        int totalPage=total/size+1;
        int[] arr=new int[totalPage];
        for(int i=0;i<arr.length;i++){
            arr[i]=i+1;
        }
        mv.addObject("totalPage",totalPage);
        mv.addObject("total",total);
        mv.addObject("arr",arr);
        mv.addObject("page",page);
        mv.addObject("size",size);
        mv.addObject("goodsEntities",goodsEntities);
        mv.setViewName("admin/g_list.html");
        return  mv;
    }


    @GetMapping("addGoods")//后台-增加商品
    public ModelAndView getAllgoods() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/g_infoAdd.html");
        return  mv;
    }

    @GetMapping("order")//后台查看-订单列表
    public ModelAndView order() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/or_list.html");
        return  mv;
    }

    @GetMapping("orderservice")//后台查看-订单售后
    public ModelAndView orderservice() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/se_list.html");
        return  mv;
    }

    @GetMapping("info")//后台查看-信息管理
    public ModelAndView info() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/r_info.html");
        return  mv;
    }


    @GetMapping("attendce")//后台查看-考勤管理
    public ModelAndView attendce() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/r_attence.html");
        return  mv;
    }


    @GetMapping("left")//后台查看-出差审核
    public ModelAndView left() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/r_leave.html");
        return  mv;
    }

    @GetMapping("question")//后台查看-技术咨询
    public ModelAndView question() {
        ModelAndView mv=new ModelAndView();
        SuggestSearch suggestSearch=new SuggestSearch();

        suggestSearch.setType(2);
        List<SuggestEntity> suggestEntities=suggestMapper.selectAllBy(suggestSearch);
//        List<String> date=null;
//        String str;
//        for(int i=0;i<suggestEntities.size();i++){
//            str=getDateByStr3(suggestEntities.get(i).getCreateTime());
//            date.add(str);
//        }
//        mv.addObject("date",date);
        mv.addObject("suggestEntities",suggestEntities);
        mv.setViewName("admin/z_consult.html");
        return  mv;
    }
    @GetMapping("questionSelect")//后台查看-技术咨询 json
    @ResponseBody
    public String questionSelect(Integer option) {
        SuggestSearch suggestSearch=new SuggestSearch();
        suggestSearch.setType(2);
        suggestSearch.setStatus(option);
        List<SuggestEntity> suggestEntities=suggestMapper.selectAllBy(suggestSearch);
        String str=JSON.toJSONString(suggestEntities);
        return  str;
    }



    @GetMapping("questionInfo")//后台查看-查看具体某一问题咨询
    public ModelAndView questionInfo(@RequestParam("id") Long id) {

        SuggestEntity suggestEntity=new SuggestEntity();
        suggestEntity.setStatus(1);
        suggestMapper.insert(suggestEntity);//更新此问题状态->解决中

        suggestEntity=suggestMapper.selectById(id);//获得此问题具体信息

        SuggestTopicSearch suggestTopicSearch=new SuggestTopicSearch();//获取此咨询问题的所有咨询
        suggestTopicSearch.setSuggestId(suggestEntity.getId());
        List<SuggestTopicEntity> suggestTopicEntities=suggestTopicMapper.selectByPage(suggestTopicSearch);

        ModelAndView mv=new ModelAndView();
        mv.addObject("suggestEntity",suggestEntity);

        mv.addObject("suggestTopicEntities",suggestTopicEntities);
        System.out.println("suggestTopicEntities:"+suggestTopicEntities.toString());
        mv.setViewName("admin/z_consultDetail.html");
        return  mv;
    }


    @GetMapping("gift")//后台查看-礼品领取记录
    public ModelAndView gift() {
        ModelAndView mv=new ModelAndView();

        mv.setViewName("admin/gi_list.html");
        return  mv;
    }



}
