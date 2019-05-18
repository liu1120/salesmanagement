package com.zzlbe.core.business.impl;

import com.zzlbe.core.business.GiftBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.dao.entity.GiftEntity;
import com.zzlbe.dao.mapper.GiftMapper;
import com.zzlbe.dao.mapper.OrderMapper;
import com.zzlbe.dao.mapper.UserMapper;
import com.zzlbe.dao.search.GiftSearch;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("giftBusiness")
public class GiftBusinessImpl extends BaseBusinessImpl implements GiftBusiness {

    @Resource
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private GiftMapper giftMapper;

    @Override
    public GenericResponse giftSelectByPage(Integer page) {
        GiftSearch giftSearch=new GiftSearch();
        if (page!=null)giftSearch.setPage(page);
        List<GiftEntity> giftEntities=giftMapper.selectByPage(giftSearch);
        return GenericResponse.SUCCESS;
    }
}
