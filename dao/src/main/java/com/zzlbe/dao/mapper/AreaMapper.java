package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.AreaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaMapper {
    List<AreaEntity> selectBySpid(long spid);//销售员id查询所派送/管理区域（多个）
    List<AreaEntity> selectProvince();
    List<AreaEntity> selectCity(long provincecode);
    List<AreaEntity> selectCounty(long citycode);
    List<AreaEntity> selectTown(long countycode);
    AreaEntity selectspid(long towncode);

    AreaEntity selectOne(long towncode);

    //更新数据管理员信息
}
