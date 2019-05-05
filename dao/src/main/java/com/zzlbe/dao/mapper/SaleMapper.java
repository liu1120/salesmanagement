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

    SaleEntity selectById(long id);


    /**
     * 分页相关
     */
    List<SaleEntity> selectByPage(SaleSearch saleSearch);

    Integer selectByPageTotal(SaleSearch saleSearch);

    List<GoodsSaleSearch> select2ByPage(SaleSearch saleSearch);//tb_goods表 tb_sale表关联查询


}
