package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.TripEntity;
import com.zzlbe.dao.search.TripEntitySearch;
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

    void delete(Long id);

    TripEntity selectById(Long id);

    TripEntitySearch select2ById(Long id);//根据id 联查

    List<TripEntity> selectByPage(TripSearch tripSearch);

    Integer selectByPageTotal(TripSearch search);

    List<TripEntitySearch> select2ByPage(TripSearch tripSearch);

}
