package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class SuggestQuerySearch extends BasePageRequest {//用于查询，日期date->String

    /**
     * 自增ID
     */
    private Long id;
    /**
     * 类型 0投诉，1建议，2技术咨询
     */
    private Integer type;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 用户编号
     */
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
    private String createTime;
    /**
     * 创建时间
     */
    private String updateTime;
    /**
     * 状态 0未处理，1处理中，2处理好
     */
    private Integer status;
}
