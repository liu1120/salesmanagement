package com.zzlbe.web.controller;

import com.zzlbe.core.business.impl.BaseBusinessImpl;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.dao.entity.GoodsEntity;
import com.zzlbe.dao.mapper.GoodsMapper;
import com.zzlbe.dao.search.GoodsSearch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 商品相关
 *
 * @author amos
 * @date 2019/4/30
 */
@RestController
@RequestMapping("goods")
public class GoodsController extends BaseBusinessImpl {

    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 查询所有商品/带分页
     */
    @PostMapping("pageAll")
    public GenericResponse pageAll(@RequestBody GoodsSearch goodsSearch) {
        List<GoodsEntity> goodsEntities = goodsMapper.selectByPage(goodsSearch);
        Integer total = goodsMapper.selectByPageTotal(goodsSearch);

        return genericPageResponse(goodsEntities, total);
    }

}
