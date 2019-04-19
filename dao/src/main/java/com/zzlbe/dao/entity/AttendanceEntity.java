package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * PROJECT: Sales
 * DESCRIPTION: 考勤打卡表
 *
 * @author duGraceful
 * @date 2019/4/13
 */
@Data
@Accessors(chain = true)
public class AttendanceEntity {

    private Long atId;
    /**
     * 销售员ID
     */
    private Long atSellerId;
    /**
     * 打卡日期
     */
    private Date atDay;
    /**
     * 1正常上班，2异常打卡，3加班，4请假，5出差
     */
    private Integer atType;
    /**
     * 上班打卡时间
     */
    private Date atStart;
    /**
     * 上班打卡类型：1正常,2迟到,3未签到。默认未签到
     */
    private Integer atStartType;
    /**
     * 下班打卡时间
     */
    private Date atEnd;
    /**
     * 下班打卡类型：1正常,2迟到,3未签到。默认未签到
     */
    private Integer atEndType;

}
