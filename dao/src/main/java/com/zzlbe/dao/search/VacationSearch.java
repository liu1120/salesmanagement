package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * PROJECT: Sales
 * DESCRIPTION: 考勤请假查询
 *
 * @author amos
 * @date 2019/4/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class VacationSearch extends BasePageRequest {

    /**
     * 销售员编号
     */
    private Long vaSellerId;
    /**
     * 开始时间
     */
    private String vaStartStr;
    /**
     * 结束时间
     */
    private String vaEndStr;
    /**
     * 请假天数
     */
    private Integer vaDays;
    /**
     * 请假理由
     */
    private String vaReason;
    /**
     * 请假类型 1事假;2病假;3调休
     */
    private Integer vaType;
    /**
     * 请假状态 0待审核，1批准，2不批准,默认0
     */
    private Integer vaStatus;
    /**
     * 请假备注
     */
    private String vaNotes;

}
