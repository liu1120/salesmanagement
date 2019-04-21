package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PROJECT: Sales
 * DESCRIPTION: 投诉建议咨询反馈表
 *
 * @author duGraceful
 * @date 2019/4/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SuggestTopicSearch extends BasePageRequest {

    /**
     * 外键ID
     * SuggestEntity#id
     */
    private Long suggestId;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户名字
     */
    private String userName;
    /**
     * 反馈内容
     */
    private String content;

}
