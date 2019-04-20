package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.CustomerEntity;
import com.zzlbe.dao.search.CustomerSearch;
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
public interface CustomerMapper {

    void insert(CustomerEntity customerEntity);

    void update(CustomerEntity customerEntity);

    CustomerEntity selectById(Long id);

    List<CustomerEntity> selectByPage(CustomerSearch customerSearch);

    Integer selectByPageTotal(CustomerSearch customerSearch);

}
