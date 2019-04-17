package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.AreaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaMapper {
    List<AreaEntity> selectBySpid(long spid);
    List<AreaEntity> selectByProvincecode(long provincecode);
    List<AreaEntity> selectByCitycode(long citycode);
    List<AreaEntity> selectByCountycode(long countycode);
}
