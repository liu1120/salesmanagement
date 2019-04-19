package com.zzlbe.web.controller;

import com.zzlbe.core.UserInfoDTO;
import com.zzlbe.core.business.UserBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.RegisterForm;
import com.zzlbe.dao.entity.UserEntity;
import com.zzlbe.dao.mapper.UserMapper;
import com.zzlbe.dao.search.UserSearch;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserMapper userMapper;

    @Resource
    private UserBusiness userBusiness;
    //依赖注入

    @GetMapping(value = "login")
    public UserEntity login(@RequestParam("username") String username,@RequestParam("password") String password) {
        //调用dao层
        UserEntity userEntity = userMapper.userLogin(username,password);
        return userEntity;
    }

    @PostMapping(value = "login")
    public UserEntity postLogin(@RequestParam("username") String username,@RequestParam("password") String password) {
        //调用dao层
        UserEntity userEntity = userMapper.userLogin(username,password);
        return userEntity;
    }

    @PostMapping("register")
    public GenericResponse register(@RequestBody RegisterForm registerForm) {
        if (registerForm == null ||
                StringUtils.isAnyBlank(
                        registerForm.getName(),
                        registerForm.getPhone(),
                        registerForm.getPassword())) {
            return GenericResponse.ERROR;
        }
        return userBusiness.register(registerForm);
    }

    @PostMapping("userAll")
    public GenericResponse userAll(@RequestBody UserSearch userSearch) {
        return userBusiness.userAll(userSearch);
    }

    @GetMapping("userInfo/{token}")
    public GenericResponse userInfo(@PathVariable("token") String token) {
        return userBusiness.userInfo(new UserInfoDTO().setToken(token));
    }

    @GetMapping("userInfoByPhone/{phone}")
    public GenericResponse userInfoByPhone(@PathVariable("phone") String phone) {
        return userBusiness.userInfo(new UserInfoDTO().setPhoneNo(phone));
    }

    @GetMapping("userInfoById/{id}")
    public GenericResponse userInfoById(@PathVariable("id") Long id) {
        return userBusiness.userInfo(new UserInfoDTO().setId(id));
    }

}
