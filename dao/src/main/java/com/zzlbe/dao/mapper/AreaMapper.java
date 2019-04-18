package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.AreaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaMapper {
    List<AreaEntity> selectBySpid(long spid);//销售员id查询所派送/管理区域（多个）
    List<AreaEntity> selectByProvincecode(long provincecode);
    List<AreaEntity> selectByCitycode(long citycode);
    List<AreaEntity> selectByCountycode(long countycode);
}
