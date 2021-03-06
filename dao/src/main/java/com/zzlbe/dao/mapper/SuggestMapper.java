package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.SuggestEntity;
import com.zzlbe.dao.search.SuggestQuerySearch;
import com.zzlbe.dao.search.SuggestSearch;
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
public interface SuggestMapper {

    void insert(SuggestEntity suggestEntity);

    void update(SuggestEntity suggestEntity);

    SuggestEntity selectById(Long id);


    List<SuggestEntity> selectByPage(SuggestSearch suggestSearch);

    List<SuggestQuerySearch> select2ByPage(SuggestSearch suggestSearch);//日期Date->String查询

    Integer selectByPageTotal(SuggestSearch suggestSearch);

    int suggestionCount();

    int suggestionUndeal1();

    int suggestionUndeal2();

    List<SuggestEntity> selectAllBy(SuggestSearch suggestSearch);

}
