package com.zzlbe.core.business;

import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.dao.entity.SuggestEntity;
import com.zzlbe.dao.entity.SuggestTopicEntity;
import com.zzlbe.dao.search.SuggestSearch;
import com.zzlbe.dao.search.SuggestTopicSearch;

/**
 * PROJECT: Sales
 * DESCRIPTION: 投诉建议技术咨询
 *
 * @author amos
 * @date 2019/4/21
 */
public interface SuggestBusiness {

    /**
     * 创建 [投诉/建议/技术咨询]
     *
     * @param suggestEntity SuggestEntity
     * @return GenericResponse
     */
    GenericResponse create(SuggestEntity suggestEntity);

    /**
     * 创建回复 [投诉/建议/技术咨询]
     *
     * @param suggestTopicEntity SuggestTopicEntity
     * @return GenericResponse
     */
    GenericResponse reply(SuggestTopicEntity suggestTopicEntity);

    /**
     * 关闭主题, 由问题发起人操作关闭 [投诉/建议/技术咨询]
     *
     * @param userId    用户编号
     * @param suggestId 主题编号
     * @return GenericResponse
     */
    GenericResponse finish(Long userId, Long suggestId);

    /**
     * 查询单个问题详情
     *
     * @param id 问题id
     * @return GenericResponse
     */
    GenericResponse findSuggest(Long id);

    /**
     * 查询单个回复详情
     *
     * @param id 问题回复id
     * @return GenericResponse
     */
    GenericResponse findSuggestReply(Long id);

    /**
     * 根据条件查询主题
     *
     * @param suggestSearch SuggestSearch
     * @return GenericResponse
     */
    GenericResponse findSuggest(SuggestSearch suggestSearch);

    /**
     * 根据条件查询主题回复
     *
     * @param suggestTopicSearch SuggestTopicSearch
     * @return GenericResponse
     */
    GenericResponse findSuggestReply(SuggestTopicSearch suggestTopicSearch);
}
