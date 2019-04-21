package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PROJECT: Sales
 * DESCRIPTION: 投诉建议咨询表
 *
 * @author duGraceful
 * @date 2019/4/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SuggestSearch extends BasePageRequest {

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
     * 状态 0未处理，1处理中，2处理好
     */
    private Integer status;

}
