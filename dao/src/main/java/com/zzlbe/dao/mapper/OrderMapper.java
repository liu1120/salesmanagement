package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.OrderEntity;
import com.zzlbe.dao.search.*;
import com.zzlbe.dao.vo.OrderGoodsVO;
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
public interface OrderMapper {

    void insert(OrderEntity orderEntity);

    void update(OrderEntity orderEntity);

    OrderEntity selectById(Long id);

    List<AmountSearch> getTotalAmountByMonth();

    List<OrderEntity> selectByUid(Long id);

    List<OrderGoodsVO> selectByUid2(Long id);

    OrderEntity selectByExample(OrderSearch orderSearch);

    List<OrderEntity> selectListByExample(OrderSearch orderSearch);

    List<OrderEntity> selectByPage(OrderSearch orderSearch);

    List<Order2Search> select2ByPage(OrderSearch orderSearch);

    Integer selectByPageTotal(OrderSearch orderSearch);

    List<Goodsselltop10Search> getGoodSellTop10();

    List<GoodssellSearch> getGoodSell(long id);

    List<Sellertop10Search> getSellerTop10();

    List<GoodssellSearch> getSellerSell(long id);

    List<CreditGetByOrderVO> selectCreditByUser(@Param("userId") Long userId,
                                                @Param("orderStatusList") List<Integer> orderStatusList);

    Long selectCreditByUserTotal(@Param("userId") Long userId,
                                 @Param("orderStatusList") List<Integer> orderStatusList);

}
