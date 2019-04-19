package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.TripEntity;
import com.zzlbe.dao.search.TripSearch;
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
public interface TripMapper {

    void insert(TripEntity userEntity);

    void update(TripEntity userEntity);

    TripEntity selectById(Long id);

    List<TripEntity> selectByPage(TripSearch tripSearch);

    Integer selectByPageTotal(TripSearch search);
}
