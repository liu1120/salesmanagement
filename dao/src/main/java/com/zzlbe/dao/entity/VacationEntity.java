package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * PROJECT: Sales
 * DESCRIPTION: 考勤请假表
 *
 * @author duGraceful
 * @date 2019/4/13
 */
@Data
@Accessors(chain = true)
public class VacationEntity {

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
     * 请假状态
     */
    private Integer vaStatus;
    /**
     * 请假备注
     */
    private String vaNotes;

}
