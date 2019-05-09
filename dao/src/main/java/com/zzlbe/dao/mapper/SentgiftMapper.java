package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.SentgiftEntity;
import com.zzlbe.dao.search.GiftSearch;
import com.zzlbe.dao.search.SentgiftSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentgiftMapper {

    void insert(SentgiftEntity sentgiftEntity);

    SentgiftEntity selectById(long id);

    List<SentgiftEntity> selectByUid(long uid);

    List<SentgiftEntity> selectByPage(GiftSearch giftSearch);

    List<SentgiftSearch> select2ByPage(GiftSearch giftSearch);//三表联查

    Integer selectByPageTotal(GiftSearch giftSearch);

}
