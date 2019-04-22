package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.SaleEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface SaleMapper {

    SaleEntity selectSaleById(long goodsid);

    void insert(SaleEntity SaleEntity);

}
