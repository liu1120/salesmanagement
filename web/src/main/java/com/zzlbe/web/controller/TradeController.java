package com.zzlbe.web.controller;

import com.zzlbe.dao.entity.GoodsEntity;
import com.zzlbe.dao.mapper.GoodsMapper;
import com.zzlbe.dao.search.GoodsSearch;
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
        goodsEntity.setNewImgPath("/Photos/"+path);
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

        System.out.println("goodsEntity=="+goodsEntity.toString());
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

    @RequestMapping("selectGoodsById")//后台查看-具体某一商品
    public ModelAndView selectGoodsById(@RequestParam("id") long id) {
        ModelAndView mv=new ModelAndView();
        GoodsEntity goodsEntity=goodsMapper.selectById(id);
        System.out.println("goodsEntity:"+goodsEntity.getIntroduce());
        mv.addObject("goods",goodsEntity);
        mv.setViewName("admin/g_infoLook.html");
        return  mv;
    }

    @RequestMapping("updateGoods")//后台-修改商品
    public ModelAndView updateGoods() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/g_infoAdd.html");//跳到查询单个页面
        return  mv;
    }

    @RequestMapping("order")//后台-订单列表
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

}
