package com.zzlbe.web.controller;

import com.zzlbe.core.business.UserBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.dto.UserInfoDTO;
import com.zzlbe.core.request.LoginForm;
import com.zzlbe.core.request.RegisterForm;
import com.zzlbe.core.request.UserInfoModifyForm;
import com.zzlbe.core.request.UserPasswordModifyForm;
import com.zzlbe.dao.entity.GoodsEntity;
import com.zzlbe.dao.entity.GoodssortEntity;
import com.zzlbe.dao.entity.SaleEntity;
import com.zzlbe.dao.entity.UserEntity;
import com.zzlbe.dao.mapper.GoodsMapper;
import com.zzlbe.dao.mapper.GoodssortMapper;
import com.zzlbe.dao.mapper.SaleMapper;
import com.zzlbe.dao.mapper.UserMapper;
import com.zzlbe.dao.search.UserSearch;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin("http://localhost:8080")
public class UserController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private SaleMapper saleMapper;
    @Resource
    private GoodssortMapper goodssortMapper;
    @Resource
    private UserBusiness userBusiness;

    //依赖注入

    @GetMapping(value = "login")
    public UserEntity login(@RequestParam("username") String username, @RequestParam("password") String password) {
        //调用dao层
        UserEntity userEntity = userMapper.userLogin(username, password);
        return userEntity;
    }

    @GetMapping(value = "getGoodsInfo")
    public GoodsEntity getGoodsInfo(@RequestParam("goodsid") long goodsid) {
        GoodsEntity goodsEntity = goodsMapper.selectById(goodsid);
        return goodsEntity;
    }

    @GetMapping(value = "selectGoodsByName")//通过商品名模糊查找
    public List<GoodsEntity> selectGoodsByName(@RequestParam("goodsname") String goodsname) {
        List<GoodsEntity> list = goodsMapper.selectGoodsByName(goodsname);
        return list;
    }

    @GetMapping(value = "getGoodsList")//查找全部商品
    public List<GoodsEntity> getGoodsList() {
        List<GoodsEntity> list = goodsMapper.selectAll();
        return list;
    }

    @GetMapping(value = "getGoodsSort")//查找全部商品分类
    public List<GoodssortEntity> getGoodsSort() {
        List<GoodssortEntity> list = goodssortMapper.selectAll();
        return list;
    }

    @GetMapping(value = "getGoodsBySort")//查找某一分类下的商品
    public List<GoodsEntity> getGoodsBySort(@RequestParam("sortid") long sortid) {
        List<GoodsEntity> list = goodsMapper.getGoodsBySort(sortid);
        return list;
    }


    @GetMapping(value = "getSaleGoods")//查找所有促销商品
    public List<GoodsEntity> getSaleGoods(@RequestParam("sortid") long sortid) {
        List<GoodsEntity> list = goodsMapper.getGoodsBySort(sortid);
        return list;
    }

    @GetMapping(value = "getSaleGoodsById")//通过商品id查找促销商品
    public SaleEntity getSaleGoodsById(@RequestParam("goodsid") long goodsid) {
        System.out.println("111");
        SaleEntity saleEntity = saleMapper.selectSaleById(goodsid);
        System.out.println("222");
        System.out.println(saleEntity);
        return saleEntity;
    }




    @PostMapping(value = "login")
    public UserEntity postLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        //调用dao层
        UserEntity userEntity = userMapper.userLogin(username, password);
        return userEntity;
    }

    @PostMapping("login2")
    public GenericResponse login2(@RequestBody @Valid LoginForm loginForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return GenericResponse.ERROR.setMessage(list.get(0).getDefaultMessage());
        }
        return userBusiness.login(loginForm);
    }

    @PostMapping("register")
    public GenericResponse register(@RequestBody @Valid RegisterForm registerForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return GenericResponse.ERROR.setMessage(list.get(0).getDefaultMessage());
        }
        return userBusiness.register(registerForm);
    }

    @PostMapping("modify")
    public GenericResponse modify(@RequestBody @Valid UserInfoModifyForm modifyForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return GenericResponse.ERROR.setMessage(list.get(0).getDefaultMessage());
        }
        return userBusiness.modify(modifyForm);
    }

    @PostMapping("passwordModify")
    public GenericResponse passwordModify(@RequestBody @Valid UserPasswordModifyForm passwordModifyForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return GenericResponse.ERROR.setMessage(list.get(0).getDefaultMessage());
        }

        return userBusiness.passwordModify(passwordModifyForm);
    }


    @PostMapping("userAll")
    public GenericResponse userAll(@RequestBody UserSearch userSearch) {
        return userBusiness.userAll(userSearch);
    }

    @PostMapping("userInfo")
    public GenericResponse userInfo(@RequestBody UserInfoDTO userInfoDTO) {
        return userBusiness.userInfo(userInfoDTO);
    }


}
