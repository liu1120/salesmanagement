package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * PROJECT: Sales
 * DESCRIPTION: 考勤出差查询
 *
 * @author amos
 * @date 2019/4/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TripSearch extends BasePageRequest {

    /**
     * 出发城市
     */
    private String trCity;
    /**
     * 出差城市
     */
    private String trToCity;
    /**
     * 出差类型
     */
    private String trType;
    /**
     * 出差目的、理由
     */
    private String trIntent;
    /**
     * 出差开始时间
     */
    private String trStartStr;
    /**
     * 出差结束时间
     */
    private String trStopStr;
    /**
     * 销售员编号
     */
    private Long trSellerId;
    /**
     * 出差申请状态（可以修改）
     * 1申请中待办，2同意，3拒绝
     */
    private Integer trState;
    /**
     * 处理理由，由审核人填写
     */
    private String trReason;

}
