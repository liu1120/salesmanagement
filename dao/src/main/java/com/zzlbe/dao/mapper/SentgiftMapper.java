package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.SentgiftEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentgiftMapper {

    void insert(SentgiftEntity sentgiftEntity);

    SentgiftEntity selectById(long id);

    List<SentgiftEntity> selectByUid(long uid);

    //List<SuggestTopicEntity> selectByPage(SuggestTopicSearch suggestTopicSearch);

}
