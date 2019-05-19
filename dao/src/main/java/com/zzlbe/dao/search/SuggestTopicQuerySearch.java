package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class SuggestTopicQuerySearch extends BasePageRequest {//查询-日期转换使用

    /**
     * 自增ID
     */
    private Long id;
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
    /**
     * 反馈时间
     */
    private String createTime;

}
