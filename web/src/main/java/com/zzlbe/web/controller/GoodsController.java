package com.zzlbe.web.controller;

import com.zzlbe.core.business.impl.BaseBusinessImpl;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.response.GiftSendVO;
import com.zzlbe.dao.entity.GiftEntity;
import com.zzlbe.dao.entity.GoodsEntity;
import com.zzlbe.dao.entity.SentgiftEntity;
import com.zzlbe.dao.mapper.GiftMapper;
import com.zzlbe.dao.mapper.GoodsMapper;
import com.zzlbe.dao.mapper.SentgiftMapper;
import com.zzlbe.dao.search.GiftSearch;
import com.zzlbe.dao.search.GoodsSearch;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PROJECT: Sales
 * DESCRIPTION: 商品相关/礼品相关
 *
 * @author amos
 * @date 2019/4/30
 */
@RestController
@RequestMapping("goods")
public class GoodsController extends BaseBusinessImpl {

    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private GiftMapper giftMapper;
    @Resource
    private SentgiftMapper sentgiftMapper;

    /**
     * 查询所有商品/带分页
     */
    @PostMapping("pageAll")
    public GenericResponse pageAll(@RequestBody GoodsSearch goodsSearch) {
        List<GoodsEntity> goodsEntities = goodsMapper.selectByPage(goodsSearch);
        Integer total = goodsMapper.selectByPageTotal(goodsSearch);

        return genericPageResponse(goodsEntities, total);
    }

    /**
     * 查询所有商品/带分页
     */
    @PostMapping("pageGift")
    public GenericResponse pageGift(@RequestBody GiftSearch giftSearch) {
        // giftName | credit
        List<GiftEntity> giftEntities = giftMapper.selectByPage(giftSearch);
        Integer total = giftMapper.selectByPageTotal(giftSearch);

        return genericPageResponse(giftEntities, total);
    }

    /**
     * 查询所有商品/带分页
     */
    @PostMapping("pageGiftSend")
    public GenericResponse pageGiftIssued(@RequestBody GiftSearch giftSearch) {
        List<GiftEntity> giftEntities = giftMapper.selectListByExample(giftSearch);
        Map<Long, GiftEntity> giftEntityMap = new HashMap<>(giftEntities.size());
        if (giftEntities.size() > 0) {
            List<Long> giftIds = new ArrayList<>();
            giftEntities.forEach(giftEntity -> {
                giftIds.add(giftEntity.getId());
                giftEntityMap.put(giftEntity.getId(), giftEntity);
            });
            giftSearch.setGiftIds(giftIds);
        }

        List<SentgiftEntity> sentGiftEntities = sentgiftMapper.selectByPage(giftSearch);
        Integer total = sentgiftMapper.selectByPageTotal(giftSearch);

        List<GiftSendVO> giftSendVOS = new ArrayList<>(sentGiftEntities.size());
        sentGiftEntities.forEach(sentGiftEntity -> {
            GiftSendVO giftSendVO = new GiftSendVO();
            BeanUtils.copyProperties(sentGiftEntity, giftSendVO);
            GiftEntity giftEntity = giftEntityMap.get(sentGiftEntity.getGiftId());
            if (giftEntity != null) {
                giftSendVO.setGiftName(giftEntity.getName());
                giftSendVO.setGiftImage(giftEntity.getImgpath());
            }
            giftSendVOS.add(giftSendVO);
        });

        return genericPageResponse(giftSendVOS, total);
    }

}
