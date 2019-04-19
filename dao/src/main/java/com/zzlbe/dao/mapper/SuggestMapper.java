package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.SuggestEntity;
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

    SuggestEntity selectById(Long id);

    List<SuggestEntity> selectByPage(SuggestEntity suggestEntity);

}
