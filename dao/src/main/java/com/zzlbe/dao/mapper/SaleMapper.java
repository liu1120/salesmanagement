package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.SaleEntity;
import com.zzlbe.dao.search.GoodsSaleSearch;
import com.zzlbe.dao.search.SaleSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleMapper {

    void insert(SaleEntity saleEntity);

    void update(SaleEntity saleEntity);

    SaleEntity selectById(Long id);

    GoodsSaleSearch select2ById(Long id);

    /**
     * 根据 县区ID 查询所有可参与的活动
     */
    List<SaleEntity> selectByCounty(Long countyCode);

    /**
     * 分页相关
     */
    List<SaleEntity> selectByPage(SaleSearch saleSearch);

    Integer selectByPageTotal(SaleSearch saleSearch);

    Integer select2ByPageTotal(SaleSearch saleSearch);//查询，根据id

    List<GoodsSaleSearch> select2ByPage(SaleSearch saleSearch);//tb_goods表 tb_sale表关联查询

}
