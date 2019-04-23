package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.GiftEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftMapper {

    void insert(GiftEntity giftEntity);

    GiftEntity selectById(Long id);

    List<GiftEntity> selectAll();

}
