package com.zzlbe.web.controller;

import com.zzlbe.dao.entity.AreaEntity;
import com.zzlbe.dao.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("user")
public class AreaController {
    @Autowired
    AreaMapper areaMapper;

    @GetMapping(value = "selectBySpid")
    public List<AreaEntity> selectBySpid(@RequestParam("spid") long spid) {
        //调用dao层
        System.out.println(spid);
        List<AreaEntity> areaEntity = areaMapper.selectBySpid(spid);
        return areaEntity;
    }

}
