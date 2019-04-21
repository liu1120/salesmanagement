package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private Long id;
    /**
     * 外键ID
     * SuggestEntity#id
     */
    @NotNull(message = "回复主题不能为空")
    private Long suggestId;
    /**
     * 用户编号
     */
    @NotNull(message = "回复用户编号不能为空")
    private Long userId;
    /**
     * 用户名字
     */
    private String userName;
    /**
     * 反馈内容
     */
    @NotBlank(message = "回复内容不能为空")
    private String content;
    /**
     * 反馈时间
     */
    private Date createTime;

}
