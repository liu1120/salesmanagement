package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.SuggestTopicEntity;
import com.zzlbe.dao.search.SuggestTopicQuerySearch;
import com.zzlbe.dao.search.SuggestTopicSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: note
 *
 * @author duGraceful
 * @date 2019/3/26
 */
@Repository
public interface SuggestTopicMapper {

    void insert(SuggestTopicEntity suggestTopicEntity);

    SuggestTopicEntity selectById(Long id);

    List<SuggestTopicEntity> selectByPage(SuggestTopicSearch suggestTopicSearch);

    List<SuggestTopicQuerySearch> select2ByPage(SuggestTopicSearch suggestTopicSearch);

    Integer selectByPageTotal(SuggestTopicSearch suggestTopicSearch);

}
