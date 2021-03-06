package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.CustomerEntity;
import com.zzlbe.dao.search.Customer2Search;
import com.zzlbe.dao.search.CustomerSearch;
import org.apache.ibatis.annotations.Param;
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

    CustomerEntity selectByOrder(@Param("orderId") Long orderId, @Param("reason") Integer reason);

    CustomerEntity selectByOrderId(@Param("orderId") Long orderId);

    List<CustomerEntity> selectByPage(CustomerSearch customerSearch);
    List<Customer2Search> select2ByPage(CustomerSearch customerSearch);//多表联查

    Integer selectByPageTotal(CustomerSearch customerSearch);

}
