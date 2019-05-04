package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.GoodssortEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodssortMapper {

    List<GoodssortEntity> selectAll();

    GoodssortEntity selectById(long id);

    void insert(GoodssortEntity goodssortEntity);
//
    void update(GoodssortEntity goodssortEntity);
}
