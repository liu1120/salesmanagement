package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.GoodsEntity;
import com.zzlbe.dao.search.GoodsSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PROJECT: sales management
 * DESCRIPTION: 产品相关
 *
 * @author amos
 * @date 2019/4/21
 */
@Repository
public interface GoodsMapper {
    void insert(GoodsEntity goodsEntity);

    void update(GoodsEntity goodsEntity);

    GoodsEntity selectById(Long id);

    GoodsEntity selectByExample(GoodsEntity search);

    List<GoodsEntity> selectByPage(GoodsEntity search);//?无用--liu

    List<GoodsEntity> selectAllByPage(GoodsSearch goodsSearch);

    List<GoodsEntity> selectAll();

    Integer selectByPageTotal(GoodsEntity search);

    Integer selectTotal();

    List<GoodsEntity> selectGoodsByName(String goodsname);

    List<GoodsEntity> getGoodsBySort(long sortid);

}
