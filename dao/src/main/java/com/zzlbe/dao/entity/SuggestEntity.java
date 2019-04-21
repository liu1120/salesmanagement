package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * PROJECT: Sales
 * DESCRIPTION: 投诉建议咨询表
 *
 * @author duGraceful
 * @date 2019/4/13
 */
@Data
@Accessors(chain = true)
public class SuggestEntity {

    /**
     * 自增ID
     */
    private Long id;
    /**
     * 类型 0投诉，1建议，2技术咨询
     */
    @NotNull(message = "类型不能为空")
    private Integer type;
    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;
    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;
    /**
     * 用户编号
     */
    @NotNull(message = "发表人用户编号不能为空")
    private Long userId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 要投诉的销售员编号
     */
    private Long sellerId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建时间
     */
    private Date updateTime;
    /**
     * 状态 0未处理，1处理中，2处理好
     */
    private Integer status;

}
