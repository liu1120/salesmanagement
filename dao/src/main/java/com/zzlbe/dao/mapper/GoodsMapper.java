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

    List<GoodsEntity> selectListByExample(GoodsSearch goodsSearch);

    List<GoodsEntity> selectAllByPage(GoodsSearch goodsSearch);

    List<GoodsEntity> selectAll();

    Integer selectTotal();

    List<GoodsEntity> selectGoodsByName(String goodsname);

    List<GoodsEntity> getGoodsBySort(Long sortid);

    /**
     * 分页查询方法
     * selectByPage：根据查询条件以及分页参数获取 List<GoodsEntity>
     * selectByPageTotal：根据查询条件获取总的数据条数 Integer
     */
    List<GoodsEntity> selectByPage(GoodsSearch goodsSearch);

    Integer selectByPageTotal(GoodsSearch goodsSearch);

}
