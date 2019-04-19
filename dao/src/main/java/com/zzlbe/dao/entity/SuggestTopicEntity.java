package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * PROJECT: Sales
 * DESCRIPTION: 投诉建议咨询反馈表
 *
 * @author duGraceful
 * @date 2019/4/13
 */
@Data
@Accessors(chain = true)
public class SuggestTopicEntity {

    /**
     * 自增ID
     */
    private Long toId;
    /**
     * 销售员编号
     */
    private Long toSellerId;
    /**
     * 反馈时间
     */
    private Date toTime;
    /**
     * 反馈内容
     */
    private String toContent;
    /**
     * 用户编号
     */
    private Long toUserId;
    /**
     * 用户名字
     */
    private String toUserName;

}
