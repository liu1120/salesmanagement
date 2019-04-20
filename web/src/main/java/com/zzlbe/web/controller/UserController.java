package com.zzlbe.web.controller;

import com.zzlbe.core.UserInfoDTO;
import com.zzlbe.core.business.UserBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.LoginForm;
import com.zzlbe.core.request.RegisterForm;
import com.zzlbe.dao.entity.UserEntity;
import com.zzlbe.dao.mapper.UserMapper;
import com.zzlbe.dao.search.UserSearch;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserBusiness userBusiness;
    //依赖注入

    @GetMapping(value = "login")
    public UserEntity login(@RequestParam("username") String username, @RequestParam("password") String password) {
        //调用dao层
        UserEntity userEntity = userMapper.userLogin(username, password);
        return userEntity;
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
