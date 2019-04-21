package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.SellerEntity;
import com.zzlbe.dao.search.SellerSearch;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SellerMapper {

    SellerEntity selectSeller(String username);

    void insert(SellerEntity SellerEntity);

    SellerEntity selectById(Long id);

    SellerEntity selectByPhoneNo(String phoneNo);

    List<SellerEntity> selectByPage(SellerSearch sellerSearch);

    int adminerCount();

    int sellerCount();
}
