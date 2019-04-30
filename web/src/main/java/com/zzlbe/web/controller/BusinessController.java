package com.zzlbe.web.controller;

import com.alibaba.fastjson.JSON;
import com.zzlbe.core.util.MD5Utils;
import com.zzlbe.dao.entity.AreaEntity;
import com.zzlbe.dao.entity.SellerEntity;
import com.zzlbe.dao.mapper.AreaMapper;
import com.zzlbe.dao.mapper.SellerMapper;
import com.zzlbe.dao.search.SellerSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BusinessController {
    @Autowired
    SellerMapper sellerMapper;
    @Autowired
    AreaMapper areaMapper;

    /**
     * R5.0
     * 后台查看-信息管理
     *
     * @param pageNo
     * @return
     */
    @GetMapping("info")//产看员工信息列表
    public ModelAndView info(Integer pageNo) {
        ModelAndView mv = new ModelAndView();
        SellerSearch sellerSearch = new SellerSearch();
        if (pageNo != null) {
            sellerSearch.setPage(pageNo);
        }
//        sellerSearch.setRank("0");
        List<SellerEntity> sellerEntities = sellerMapper.selectByPage(sellerSearch);
        Integer total = sellerMapper.selectByPageTotal(sellerSearch);
        int size = sellerSearch.getSize(); //页码大小
        int page = sellerSearch.getPage(); //当前页
        int totalPage = total / size + 1;
        int[] arr = new int[totalPage];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        mv.addObject("arr", arr);
        mv.addObject("totalPage", totalPage);
        mv.addObject("total", total);
        mv.addObject("size", size);
        mv.addObject("page", page);
        mv.addObject("sellerEntities", sellerEntities);

        System.out.println("sellerEntities:" + sellerEntities);
        mv.setViewName("admin/r_info.html");
        return mv;
    }


    @GetMapping("infoLook")//后台查看-查看某一员工信息
    public ModelAndView infoLook(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView();
        SellerEntity sellerEntity = sellerMapper.selectById(id);
        mv.addObject("seller", sellerEntity);
        mv.setViewName("admin/r_infoLook.html");
        return mv;
    }


    @GetMapping("infoUpdate")//后台查看-修改某一员工信息
    public ModelAndView infoUpdate(@RequestParam("id") Long id, String message) {
        ModelAndView mv = new ModelAndView();
        SellerEntity sellerEntity = sellerMapper.selectById(id);
        mv.addObject("seller", sellerEntity);
        if (message != null || message != "") {
            mv.addObject("message", message);
        }
        mv.setViewName("admin/r_infoUpdate.html");
        return mv;
    }

    @ResponseBody
    @PostMapping("infoSave")//后台查看-保存修改某一员工信息
    public ModelAndView infoSave(Long id, String name, String realname, String phone, String post, String position) {
        ModelAndView mv = new ModelAndView();
        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setName(name);
        sellerEntity.setRealname(realname);
        sellerEntity.setPost(post);
        sellerEntity.setPosition(position);
        sellerEntity.setPhone(phone);
        sellerEntity.setId(id);

        sellerMapper.update(sellerEntity);
        mv.setViewName("redirect:/admin/infoUpdate");
        mv.addObject("message", "成功");
        mv.addObject("id", id);
        return mv;
    }

    @GetMapping("infoAdd")//后台查看-添加员工
    public ModelAndView infoAdd() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/r_infoAdd.html");
        return mv;
    }

    @ResponseBody
    @PostMapping("addSeller")//后台查看-添加员工,进行保存
    public ModelAndView addSeller(Long id, String name, String realname, String phone, String post, String position) {
        ModelAndView mv = new ModelAndView();
        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setName(name);
        sellerEntity.setRealname(realname);
        sellerEntity.setPost(post);
        sellerEntity.setPosition(position);
        sellerEntity.setPhone(phone);
        sellerEntity.setId(id);

        MD5Utils md = new MD5Utils();
        sellerEntity.setPassword(md.getMD5("123456"));
        sellerMapper.insert(sellerEntity);
//        mv.setViewName("redirect:/admin/infoAdd");
        mv.addObject("message", "成功");
        mv.addObject("id", id);
        mv.setViewName("admin/r_infoAdd.html");
        return mv;
    }


    @GetMapping("infoAssign")//后台查看-为员工划分区域
    public ModelAndView infoAssign(@RequestParam("id") Long spid) {
        ModelAndView mv = new ModelAndView();
        SellerEntity sellerEntity = sellerMapper.selectById(spid);
        mv.addObject("seller", sellerEntity);

        List<AreaEntity> areaEntities = areaMapper.selectBySpid(spid);
        mv.addObject("areas", areaEntities);

        mv.setViewName("admin/r_divideArea.html");
        return mv;
    }

    @PostMapping("saveAssign")//后台查看-为员工划分区域进行保存
    public ModelAndView saveAssign(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView();
//        SellerEntity sellerEntity=sellerMapper.selectById(id);
        mv.setViewName("admin/r_divideArea.html");
        return mv;
    }


    @RequestMapping("selectProvince")//省份去重.选省份。
    @ResponseBody
    public String selectProvince() {
        List<AreaEntity> areaEntities = areaMapper.selectProvince();
        String str = JSON.toJSONString(areaEntities);
        return str;
    }

    @RequestMapping("selectCity")//城市去重.选城市。
    @ResponseBody
    public String selectCity(@RequestParam("provincecode") Long provincecode) {
        List<AreaEntity> areaEntities = areaMapper.selectCity(provincecode);
        String str = JSON.toJSONString(areaEntities);
        return str;
    }

    @RequestMapping("selectTown")//区县
    @ResponseBody
    public String selectTown(@RequestParam("countycode") long countycode) {
        //调用dao层
        List<AreaEntity> areaEntities = areaMapper.selectTown(countycode);
        String str = JSON.toJSONString(areaEntities);
        return str;
    }
}
