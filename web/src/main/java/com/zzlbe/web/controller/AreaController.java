package com.zzlbe.web.controller;

import com.google.gson.Gson;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.dto.AreaDTO;
import com.zzlbe.dao.entity.AreaEntity;
import com.zzlbe.dao.mapper.AreaMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("area")
public class AreaController {

    @Resource
    private AreaMapper areaMapper;

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

    @GetMapping("selectAll")
    public GenericResponse selectAll() throws IOException {
        List<AreaEntity> areaEntities = areaMapper.selectAll();

        return new GenericResponse<>(getAreaData(areaEntities));
    }

    private String getAreaData(List<AreaEntity> areaEntities) throws IOException {
        long startTime = System.currentTimeMillis();

        // 省份 Map
        Map<Long, AreaDTO> provinceMap = new ConcurrentHashMap<>();
        List<AreaDTO> provinceList = new ArrayList<>();

        areaEntities.forEach(entity -> {
            // 省份信息
            AreaDTO province = provinceMap.get(entity.getProvincecode());
            if (province == null) {
                province = new AreaDTO(entity.getProvincecode(), entity.getProvincename());
                provinceList.add(province);
                provinceMap.put(entity.getProvincecode(), province);
            }

            // 城市 Map
            Map<Long, AreaDTO> cityMap = province.getChildren();
            AreaDTO city = cityMap.get(entity.getCitycode());
            if (city == null) {
                city = new AreaDTO(entity.getCitycode(), entity.getCityname());
                province.getList().add(city);
                cityMap.put(entity.getCitycode(), city);
            }

            // 县级市 Map
            Map<Long, AreaDTO> countyMap = city.getChildren();
            AreaDTO county = countyMap.get(entity.getCountycode());
            if (county == null) {
                county = new AreaDTO(entity.getCountycode(), entity.getCountyname());
                city.getList().add(county);
                countyMap.put(entity.getCountycode(), county);
            }

            // 乡镇 Map
            Map<Long, AreaDTO> townMap = county.getChildren();
            AreaDTO town = townMap.get(entity.getTowncode());
            if (town == null) {
                town = new AreaDTO(entity.getTowncode(), entity.getTownname());
                county.getList().add(town);
                townMap.put(entity.getTowncode(), town);
            }
        });

        String allData = removeChildren(provinceList);

        log.info(">>>>>>>> 获取所有地区信息, 耗时 {} 毫秒~~~", (System.currentTimeMillis() - startTime));

        return allData;
    }

    private String removeChildren(List<AreaDTO> areaDTOList) throws IOException {
        areaDTOList.forEach(area1 -> {
            area1.setChildren(null);
            List<AreaDTO> area1List = area1.getList();
            area1List.forEach(area2 -> {
                area2.setChildren(null);
                List<AreaDTO> area2List = area2.getList();
                area2List.forEach(area3 -> area3.setChildren(null));
            });
        });

        return new Gson().toJson(areaDTOList);
    }


}
