package com.zzlbe.web.controller;

import com.zzlbe.dao.mapper.SellerMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("seller")
@CrossOrigin("http://localhost:8080")
public class SellerController {
    @Resource
    private SellerMapper sellerMapper;

}
