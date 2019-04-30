package com.zzlbe.web.controller;

import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.dao.entity.AreaEntity;
import com.zzlbe.dao.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("area")
public class AreaController {
    @Autowired
    AreaMapper areaMapper;

    @GetMapping(value = "selectBySpid")//查询销售员的区域
    public GenericResponse selectBySpid(@RequestParam("spid") long spid) {
        //调用dao层
        List<AreaEntity> areaEntity = areaMapper.selectBySpid(spid);
        return new GenericResponse<>(areaEntity);
    }

    @GetMapping(value = "selectProvince")//省份去重.选省份。
    public GenericResponse selectProvince() {
        List<AreaEntity> areaEntitys = areaMapper.selectProvince();
        return new GenericResponse<>(areaEntitys);
    }

    @GetMapping(value = "selectCity")//城市去重
    public GenericResponse selectCity(@RequestParam("provincecode") long provincecode) {
        List<AreaEntity> areaEntity = areaMapper.selectCity(provincecode);
        return new GenericResponse<>(areaEntity);
    }
    @GetMapping(value = "selectCounty")//城镇去重
    public GenericResponse selectCounty(@RequestParam("citycode") long citycode) {
        //调用dao层
        List<AreaEntity> areaEntity = areaMapper.selectCounty(citycode);
        return new GenericResponse<>(areaEntity);
    }

    @GetMapping(value = "selectTown")//区县
    public GenericResponse selectTown(@RequestParam("countycode") long countycode) {
        //调用dao层
        List<AreaEntity> areaEntity = areaMapper.selectTown(countycode);
        return new GenericResponse<>(areaEntity);
    }

}
