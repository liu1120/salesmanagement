package com.zzlbe.core.request;

import lombok.Data;

import java.util.Date;

/**
 * PROJECT: Sales
 * DESCRIPTION: 请假
 *
 * @author amos
 * @date 2019/4/14
 */
@Data
public class AttendanceVacationForm {

    /**
     * 自增ID
     */
    private Long vaId;
    /**
     * 销售员编号
     */
    private Long vaSellerId;
    /**
     * 开始时间
     */
    private Date vaStart;
    /**
     * 结束时间
     */
    private Date vaEnd;
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
