package com.zzlbe.web.controller;

import com.alibaba.fastjson.JSON;
import com.zzlbe.core.response.AreaVO;
import com.zzlbe.core.util.DateUtil;
import com.zzlbe.dao.entity.*;
import com.zzlbe.dao.mapper.*;
import com.zzlbe.dao.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TradeController {

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    GoodssortMapper goodssortMapper;
    @Autowired
    AreaMapper areaMapper;
    @Autowired
    SellerMapper sellerMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    GiftMapper giftMapper;


    @GetMapping("getAllgoods")//后台查看-商品列表
    public ModelAndView getAllgoods(Integer pageNo) {
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



    @RequestMapping("addGoods")//后台-增加商品
    public ModelAndView getAllgoods() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/g_infoAdd.html");
        return  mv;
    }

    @RequestMapping("saveGoodsInfo")//后台-增加商品 进行保存
    public ModelAndView saveGoodsInfo(String path, String name, String sort, String version, Integer num, String ueInfo, BigDecimal price, BigDecimal minprice, Integer isshow, Integer credit) {
        ModelAndView mv=new ModelAndView();
        GoodsEntity goodsEntity=new GoodsEntity();
//        goodsEntity.setNewImgPath("https://api.anthub.top/Photos/"+path);
        goodsEntity.setNewImgPath("/Photos/"+path);
//        goodsEntity.setNewImgPath(path);
        goodsEntity.setName(name);
        goodsEntity.setCredit(credit);
        goodsEntity.setSort(sort);
        goodsEntity.setVersion(version);
        goodsEntity.setNum(num);
        goodsEntity.setPoint(0);
        goodsEntity.setIntroduce(ueInfo);
        goodsEntity.setPrice(price);
        goodsEntity.setMinPrice(minprice);
        goodsEntity.setIsShow(isshow);
        goodsEntity.setUpdateTime(new Date());
        goodsMapper.insert(goodsEntity);

        mv.addObject("message","商品"+goodsEntity.getName()+"添加成功");
        mv.setViewName("redirect:/admin/getAllgoods");
        return  mv;
    }


    @RequestMapping("deleteGoods")//后台-下架商品
    public ModelAndView deleteGoods(@RequestParam("id") long id) {
        ModelAndView mv=new ModelAndView();
        GoodsEntity goodsEntity=goodsMapper.selectById(id);

        int flag=goodsEntity.getIsShow();
        if(flag==0){ goodsEntity.setIsShow(1); }else{ goodsEntity.setIsShow(0); }
        goodsMapper.update(goodsEntity);
        mv.addObject("message",goodsEntity.getName()+":下架成功");
        mv.setViewName("redirect:/admin/getAllgoods");
        return  mv;
    }
    @RequestMapping("deleteGifts")//后台-下架礼品
    public ModelAndView deleteGifts(@RequestParam("id") long id) {
        ModelAndView mv=new ModelAndView();
        GiftEntity giftEntity=giftMapper.selectById(id);

        int flag=giftEntity.getIsshow();
        if(flag==0){ giftEntity.setIsshow(1); }else{ giftEntity.setIsshow(0); }
        giftMapper.update(giftEntity);
        mv.addObject("message",giftEntity.getName()+":下架成功");
        mv.setViewName("redirect:/admin/getGiftList");
        return  mv;
    }
    @RequestMapping("selectGoodsById")//后台查看-具体某一商品
    public ModelAndView selectGoodsById(@RequestParam("id") long id) {
        ModelAndView mv=new ModelAndView();
        GoodsEntity goodsEntity=goodsMapper.selectById(id);

        mv.addObject("goods",goodsEntity);
        Date date=goodsEntity.getUpdateTime();
        DateUtil du=new DateUtil();
        String dateStr=du.getDateTime(date);
        mv.addObject("time",dateStr);
        mv.setViewName("admin/g_infoLook.html");
        return  mv;
    }

    @RequestMapping("updateGoods")//后台-修改商品 修改农产品详情
    public ModelAndView updateGoods(@RequestParam("id") long id) {
        ModelAndView mv=new ModelAndView();
        GoodsEntity goodsEntity=goodsMapper.selectById(id);
        mv.addObject("goods",goodsEntity);
        mv.setViewName("admin/g_infoUpdate.html");//跳到查询单个页面
        return  mv;
    }

    @RequestMapping("saveUpdateGoodsInfo")//后台-更新商品update 进行保存
    public ModelAndView saveUpdateGoodsInfo(String name,@RequestParam("id") Long id, String sort, String version, Integer num, String introduce, BigDecimal price, BigDecimal minprice, Integer credit) {
        ModelAndView mv=new ModelAndView();
        GoodsEntity goodsEntity=new GoodsEntity();
        goodsEntity.setName(name);
        goodsEntity.setId(id);
        goodsEntity.setSort(sort);
        goodsEntity.setVersion(version);
        goodsEntity.setNum(num);
        goodsEntity.setIntroduce(introduce);
        goodsEntity.setUpdateTime(new Date());
        goodsEntity.setPrice(price);
        goodsEntity.setMinPrice(minprice);
        goodsEntity.setCredit(credit);
        goodsMapper.update(goodsEntity);

        mv.addObject("message","商品"+goodsEntity.getName()+"修改成功");
        mv.addObject("id",goodsEntity.getId());
        mv.setViewName("redirect:/admin/selectGoodsById");
        return  mv;
    }

    @RequestMapping("order")//后台-订单列表
    public ModelAndView order(Integer pageNo, Integer type) {
        ModelAndView mv=new ModelAndView();
        OrderSearch orderSearch=new OrderSearch();
        if (pageNo!=null){
            orderSearch.setPage(pageNo);
        }
        if (type != null) {
            if (type != 8)
                orderSearch.setOrType(type);
        } else {
            type = new Integer(8);
        }
        mv.addObject("status", type);
        List<Order2Search> order2=orderMapper.select2ByPage(orderSearch);

        Integer total=orderMapper.selectByPageTotal(orderSearch);
        int size=orderSearch.getSize();//页码大小
        int page=orderSearch.getPage();//当前页
        int totalPage=total/size+1;
        int[] arr=new int[totalPage];
        for(int i=0;i<arr.length;i++){
            arr[i]=i+1;
        }
        mv.addObject("totalPage",totalPage);
        mv.addObject("arr",arr);
        mv.addObject("total",total);
        mv.addObject("page",page);
        mv.addObject("size",size);
        mv.addObject("order",order2);
        mv.setViewName("admin/or_list.html");
        return  mv;
    }

    @GetMapping("orderservice")//后台查看-订单售后查询
    public ModelAndView orderservice() {
        ModelAndView mv=new ModelAndView();
        CustomerSearch customerSearch=new CustomerSearch();
        List<Customer2Search> customer2Search=customerMapper.select2ByPage(customerSearch);

        Integer total=customerMapper.selectByPageTotal(customerSearch);
        int size=customerSearch.getSize();//页码大小
        int page=customerSearch.getPage();//当前页
        int totalPage=total/size+1;
        int[] arr=new int[totalPage];
        for(int i=0;i<arr.length;i++){
            arr[i]=i+1;
        }
        mv.addObject("totalPage",totalPage);
        mv.addObject("arr",arr);
        mv.addObject("total",total);
        mv.addObject("page",page);
        mv.addObject("size",size);
        mv.addObject("customers",customer2Search);
        mv.setViewName("admin/se_list.html");
        return  mv;
    }

    @GetMapping("getGiftList")//后台查看-礼品列表
    public ModelAndView getGiftList(Integer pageNo) {
        GiftSearch giftSearch=new GiftSearch();
        if (pageNo!=null){giftSearch.setPage(pageNo);}
        System.out.println("pageNo:"+giftSearch.getPage());
        ModelAndView mv=new ModelAndView();
        List<GiftEntity> giftEntities=giftMapper.selectByPage(giftSearch);

        Integer total=giftMapper.selectByPageTotal(giftSearch);
        int page=giftSearch.getPage();//当前页
        int size=giftSearch.getSize();//页码大小

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
        mv.addObject("giftEntities",giftEntities);
        mv.setViewName("admin/gift_list.html");
        System.out.println("mv:"+mv);
        return  mv;
    }
    @RequestMapping("selectGiftById")//后台查看-具体某一礼品
    public ModelAndView selectGiftById(@RequestParam("id") long id) {
        ModelAndView mv=new ModelAndView();
        GiftEntity giftEntity=giftMapper.selectById(id);

        mv.addObject("gift",giftEntity);
        Date date=giftEntity.getEndtime();
        DateUtil du=new DateUtil();
        String dateStr=du.getDateTime(date);
        mv.addObject("time",dateStr);
        mv.setViewName("admin/gift_infoLook.html");
        return  mv;
    }
    @RequestMapping("seDetil")//后台查看-具体某一订单
    public ModelAndView seDetil(@RequestParam("id") long id) {
        ModelAndView mv=new ModelAndView();
        GiftEntity giftEntity=giftMapper.selectById(id);

        mv.addObject("gift",giftEntity);
        Date date=giftEntity.getEndtime();
        DateUtil du=new DateUtil();
        String dateStr=du.getDateTime(date);
        mv.addObject("time",dateStr);
        mv.setViewName("admin/gift_infoLook.html");
        return  mv;
    }





    @RequestMapping("addGift")//后台-增加礼品
    public ModelAndView addGift() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/gift_infoAdd.html");
        return  mv;
    }

    @RequestMapping("saveGiftInfo")//后台-增加礼品 进行保存
    public ModelAndView saveGiftInfo(String path, String name, Long credit,Long num, String ueInfo, Integer isshow) {
        ModelAndView mv=new ModelAndView();
        GiftEntity giftEntity=new GiftEntity();
//        giftEntity.setImgpath("https://api.anthub.top/Photos/"+path);
        giftEntity.setImgpath("/Photos/"+path);
        giftEntity.setImgpath(path);
        giftEntity.setName(name);
        giftEntity.setIsshow(isshow);
        giftEntity.setDescribe(ueInfo);
        giftEntity.setCredit(credit);
        giftEntity.setNum(num);
        giftEntity.setEndtime(new Date());
        giftMapper.insert(giftEntity);
        mv.addObject("message","礼品"+giftEntity.getName()+"添加成功");
        mv.setViewName("redirect:/admin/getGiftList");
        return  mv;
    }







    @GetMapping("orderInfo")//后台查看-订单查看
    public ModelAndView orderInfo(@RequestParam("id") long orid) {
        ModelAndView mv=new ModelAndView();
        Order2Search order2Search=orderMapper.select2ById(orid);

        mv.addObject("order",order2Search);
        AddressEntity addressEntity=addressMapper.selectById(order2Search.getOrAddress());

        String address=addressEntity.getAddress();
        AreaVO areavo = JSON.parseObject(address, AreaVO.class);
        AreaEntity areaEntity = areaMapper.select2One(areavo.getCountycode());
        String str=areaEntity.getProvincename()+"-"+areaEntity.getCityname()+"-"+areaEntity.getCountyname()+" "+addressEntity.getUname()+":"+addressEntity.getPhone();
        addressEntity.setAddress(str);

        UserEntity userEntity=userMapper.selectById(order2Search.getOrUserId());
        SellerEntity sellerEntity=sellerMapper.selectById(order2Search.getOrSellerId());
        GoodsEntity goodsEntity=goodsMapper.selectById(order2Search.getOrGoodsId());

        String userInfo=userEntity.getRealname();
        String sellerInfo=sellerEntity.getRealname()+" "+sellerEntity.getPhone();
        mv.addObject("userInfo",userInfo);
        mv.addObject("sellerInfo",sellerInfo);
        mv.addObject("address",addressEntity);
        mv.setViewName("admin/or_send.html");
        return  mv;
    }

}
