package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.VacationEntity;
import com.zzlbe.dao.search.VacationSearch;
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
public interface VacationMapper {

    void insert(VacationEntity userEntity);

    void update(VacationEntity userEntity);

    void delete(Long id);

    VacationEntity selectById(Long id);

    List<VacationEntity> selectByPage(VacationSearch vacationSearch);

    Integer selectByPageTotal(VacationSearch search);
}
