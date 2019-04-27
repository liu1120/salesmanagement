package com.zzlbe.core.business.impl;

import com.zzlbe.core.business.SuggestBusiness;
import com.zzlbe.core.common.ErrorCodeEnum;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.dao.entity.SuggestEntity;
import com.zzlbe.dao.entity.SuggestTopicEntity;
import com.zzlbe.dao.mapper.SuggestMapper;
import com.zzlbe.dao.mapper.SuggestTopicMapper;
import com.zzlbe.dao.search.SuggestSearch;
import com.zzlbe.dao.search.SuggestTopicSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 投诉建议技术咨询
 *
 * @author amos
 * @date 2019/4/21
 */
@Component("suggestBusiness")
public class SuggestBusinessImpl extends BaseBusinessImpl implements SuggestBusiness {
    private static Logger LOGGER = LoggerFactory.getLogger(SuggestBusinessImpl.class);

    @Resource
    private SuggestMapper suggestMapper;
    @Resource
    private SuggestTopicMapper suggestTopicMapper;


    @Override
    public GenericResponse create(SuggestEntity suggestEntity) {
        // type 类型 >>> 0投诉，1建议，2技术咨询
        switch (suggestEntity.getType()) {
            case 0:
                LOGGER.info("新消息...[投诉]{} >>>>> [被投诉人:{}]", suggestEntity.getTitle(), suggestEntity.getSellerId());
                break;
            case 1:
                LOGGER.info("新消息...[建议]{}", suggestEntity.getTitle());
                break;
            case 2:
                LOGGER.info("新消息...[技术咨询]{}", suggestEntity.getTitle());
                break;
            default:
                break;
        }

        suggestEntity.setId(null);
        // status 状态 >>>  0未处理，1处理中，2处理好
        suggestEntity.setStatus(0);
        suggestEntity.setCreateTime(new Date());
        suggestMapper.insert(suggestEntity);

        return GenericResponse.SUCCESS;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public GenericResponse reply(SuggestTopicEntity suggestTopicEntity) {
        SuggestEntity suggestEntity = suggestMapper.selectById(suggestTopicEntity.getSuggestId());
        if (suggestEntity == null) {
            return new GenericResponse(ErrorCodeEnum.SUGGEST_REPLY_ERROR);
        }
        suggestTopicEntity.setId(null);
        suggestTopicEntity.setCreateTime(new Date());
        suggestTopicMapper.insert(suggestTopicEntity);

        /*
         * 有人回复默认处理中
         * status 状态 >>>  0未处理，1处理中，2处理好
         */
        suggestEntity.setStatus(1);
        suggestEntity.setUpdateTime(new Date());
        suggestMapper.update(suggestEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse finish(Long userId, Long suggestId) {
        SuggestEntity suggestEntity = suggestMapper.selectById(suggestId);
        if (suggestEntity == null) {
            return new GenericResponse(ErrorCodeEnum.SUGGEST_CLOSE_ERROR);
        }
        if (!suggestEntity.getUserId().equals(userId)) {
            return new GenericResponse(ErrorCodeEnum.SUGGEST_CLOSE_ERROR_NOT_SPONSOR);
        }

        // status 状态 >>>  0未处理，1处理中，2处理完成
        int finishProcess = 2;

        if (suggestEntity.getStatus() == finishProcess) {
            return GenericResponse.SUCCESS;
        }

        // 有人回复默认处理中
        suggestEntity.setStatus(finishProcess);
        suggestEntity.setUpdateTime(new Date());
        suggestMapper.update(suggestEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse findSuggest(Long id) {

        return new GenericResponse<>(suggestMapper.selectById(id));
    }

    @Override
    public GenericResponse findSuggestReply(Long id) {

        return new GenericResponse<>(suggestTopicMapper.selectById(id));
    }

    @Override
    public GenericResponse findSuggest(SuggestSearch suggestSearch) {
        List<SuggestEntity> suggestEntities = suggestMapper.selectByPage(suggestSearch);
        Integer total = suggestMapper.selectByPageTotal(suggestSearch);

        return genericPageResponse(suggestEntities, total);
    }

    @Override
    public GenericResponse findSuggestReply(SuggestTopicSearch suggestTopicSearch) {
        List<SuggestTopicEntity> entities = suggestTopicMapper.selectByPage(suggestTopicSearch);
        Integer total = suggestTopicMapper.selectByPageTotal(suggestTopicSearch);

        return genericPageResponse(entities, total);
    }
}
