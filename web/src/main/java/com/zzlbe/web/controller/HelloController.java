package com.zzlbe.web.controller;

import com.zzlbe.core.util.IpUtil;
import com.zzlbe.dao.entity.AddressEntity;
import com.zzlbe.dao.mapper.AddressMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * PROJECT: Sales
 * DESCRIPTION: note
 *
 * @author duGraceful
 * @date 2019/3/26
 */
@Controller
public class HelloController {
    @Resource
    private AddressMapper addressMapper;
    @RequestMapping("/temp")
    public void temp() {
        System.out.println("temp");
//        List<AddressEntity> list =addressMapper.selectByUid(1002);
        AddressEntity ad=new AddressEntity();
        long num=12350;
        ad.setId(num);
        System.out.println(ad.getId());
        addressMapper.update(ad);
        System.out.println("list:");
    }

    /**
     * 展示ExceptionAdvice作用
     *
     * @return null
     * @see com.zzlbe.web.advice.ExceptionAdvice
     */
    @GetMapping("terror")
    public String error() {
        throw new RuntimeException("Error Request!");
    }

    @GetMapping("ip")
    public String ip(HttpServletRequest request) {
        return IpUtil.getIpAddress(request);
    }

}
