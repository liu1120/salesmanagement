package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.GoodsEntity;
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

    List<GoodsEntity> selectByPage(GoodsEntity search);

    Integer selectByPageTotal(GoodsEntity search);

}
