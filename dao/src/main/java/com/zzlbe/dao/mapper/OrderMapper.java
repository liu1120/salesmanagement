package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.OrderEntity;
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

    OrderEntity selectById(Long id);

    List<OrderEntity> selectByPage(OrderEntity orderEntity);

}
