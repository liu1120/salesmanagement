package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.AreaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaMapper {

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    List<AreaEntity> selectAll();

    /**
     * 销售员id查询所派送/管理区域（多个）
     */
    List<AreaEntity> selectBySpid(Long spid);

    List<AreaEntity> selectProvince();

    List<AreaEntity> selectCity(Long provincecode);

    List<AreaEntity> selectCounty(Long citycode);

    List<AreaEntity> selectTown(Long countycode);
//    AreaEntity selectspid(long towncode);

    AreaEntity selectOne(Long towncode);

    AreaEntity select2One(Long countycode);//根据countycode选出对应的countyname 返回1条数据

    /**
     * 更新数据管理员信息
     *
     * @param areaEntity AreaEntity
     */
    void update(AreaEntity areaEntity);

    //更新数据管理员信息,只更新负责区域
    void update2(AreaEntity areaEntity);
}
