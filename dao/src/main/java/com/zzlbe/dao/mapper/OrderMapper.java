package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.OrderEntity;
import com.zzlbe.dao.search.*;
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

    List<AmountSearch> getTotalAmountByMonth();

    List<OrderEntity> selectByUid(Long id);

    OrderEntity selectByExample(OrderSearch orderSearch);

    List<OrderEntity> selectListByExample(OrderSearch orderSearch);

    List<OrderEntity> selectByPage(OrderSearch orderSearch);

    Integer selectByPageTotal(OrderSearch orderSearch);

    List<Goodsselltop10Search> getGoodSellTop10();

    List<GoodssellSearch> getGoodSell(long id);

    List<Sellertop10Search> getSellerTop10();

    List<GoodssellSearch> getSellerSell(long id);


}
