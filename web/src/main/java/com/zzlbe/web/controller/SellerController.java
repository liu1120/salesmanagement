package com.zzlbe.web.controller;

import com.zzlbe.core.business.SellerBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.dto.UserInfoDTO;
import com.zzlbe.core.request.LoginForm;
import com.zzlbe.core.request.RegisterForm;
import com.zzlbe.core.request.UserInfoModifyForm;
import com.zzlbe.core.request.UserPasswordModifyForm;
import com.zzlbe.dao.search.SellerSearch;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author duGraceful
 */
@RestController
@RequestMapping("seller")
public class SellerController {

    @Resource
    private SellerBusiness sellerBusiness;

    @PostMapping("login")
    public GenericResponse login2(@RequestBody @Valid LoginForm loginForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            return GenericResponse.ERROR.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return sellerBusiness.login(loginForm);
    }

    @PostMapping("register")
    public GenericResponse register(@RequestBody @Valid RegisterForm registerForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            return GenericResponse.ERROR.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return sellerBusiness.register(registerForm);
    }


    @PostMapping("modify")
    public GenericResponse modify(@RequestBody @Valid UserInfoModifyForm modifyForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            return GenericResponse.ERROR.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return sellerBusiness.modify(modifyForm);
    }

    @PostMapping("passwordModify")
    public GenericResponse passwordModify(@RequestBody @Valid UserPasswordModifyForm passwordModifyForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            return GenericResponse.ERROR.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        return sellerBusiness.passwordModify(passwordModifyForm);
    }

    @PostMapping("userAll")
    public GenericResponse userAll(@RequestBody SellerSearch sellerSearch) {
        return sellerBusiness.userAll(sellerSearch);
    }

    @PostMapping("userInfo")
    public GenericResponse userInfo(@RequestBody UserInfoDTO userInfoDTO) {
        return sellerBusiness.userInfo(userInfoDTO);
    }

}
