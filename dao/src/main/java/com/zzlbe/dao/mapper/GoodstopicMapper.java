package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.GoodstopicEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GoodstopicMapper {
    List<GoodstopicEntity> selectById(long id);
    void insert(GoodstopicEntity goodstopicEntity);
    List<GoodstopicEntity> selectByGoodsid(long goodsid);

}
