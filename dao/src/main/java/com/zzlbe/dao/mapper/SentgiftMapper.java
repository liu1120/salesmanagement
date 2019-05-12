package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.SentgiftEntity;
import com.zzlbe.dao.search.GiftSearch;
import com.zzlbe.dao.search.CreditConsumeBySendGiftVO;
import com.zzlbe.dao.search.SentgiftSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentgiftMapper {

    void insert(SentgiftEntity sentgiftEntity);

    SentgiftEntity selectById(long id);

    List<SentgiftEntity> selectByUid(long uid);

    List<SentgiftEntity> selectByPage(GiftSearch giftSearch);

    List<SentgiftSearch> select2ByPage(GiftSearch giftSearch);//三表联查

    Integer selectByPageTotal(GiftSearch giftSearch);

    /**
     * 积分消费记录 By 礼品发放记录
     */
    List<CreditConsumeBySendGiftVO> sendGiftByUser(Long userId);

    /**
     * 合计消费的积分
     */
    Long sendGiftByUserTotal(Long userId);

}
