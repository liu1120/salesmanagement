package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.OrderEntity;
import com.zzlbe.dao.search.OrderSearch;
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
public interface OrderMapper {

    void insert(OrderEntity orderEntity);

    void update(OrderEntity orderEntity);

    OrderEntity selectById(Long id);

    OrderEntity getTotalAmountByMonth();

    List<OrderEntity> selectByUid(Long id);

    List<OrderEntity> selectByPage(OrderSearch orderSearch);

    Integer selectByPageTotal(OrderSearch orderSearch);

}
