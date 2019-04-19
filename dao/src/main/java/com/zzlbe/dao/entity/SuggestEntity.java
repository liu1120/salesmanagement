package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

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
    private Long suId;
    /**
     * 类型
     */
    private Integer suType;
    /**
     * 标题
     */
    private String suTitle;
    /**
     * 内容
     */
    private String suContent;
    /**
     * 用户编号
     */
    private Long suUserId;
    /**
     * 商品名称
     */
    private String suGoodsName;
    /**
     * 销售员编号
     */
    private Long suSellerId;
    /**
     * 创建时间
     */
    private Date suTime;
    /**
     * 处理结果
     */
    private Integer suIsOk;

}
