package com.zzlbe.core.constant;

/**
 * PROJECT: Sales
 * DESCRIPTION: 考勤相关常量
 *
 * @author amos
 * @date 2019/4/14
 */
public interface AttendanceConstant {

    /**
     * 1 正常上班
     * 2 异常打卡
     * 3 加班
     * 4 请假
     * 5 出差
     */
    int PUNCH_TYPE_NORMAL = 1;
    int PUNCH_TYPE_EXCEPTION = 2;
    int PUNCH_TYPE_OVERTIME = 3;
    int PUNCH_TYPE_VACATION = 4;
    int PUNCH_TYPE_TRIP = 5;

    /**
     * 开始类型：1正常,2迟到,3未签到。默认未签到
     */
    int PUNCH_TYPE_START_NORMAL = 1;
    int PUNCH_TYPE_START_LATE = 2;
    int PUNCH_TYPE_START_NONE = 3;

    /**
     * 结束类型：1正常，2早退，3未签到。默认未签到
     */
    int PUNCH_TYPE_END_NORMAL = 1;
    int PUNCH_TYPE_END_EARLY = 2;
    int PUNCH_TYPE_END_NONE = 3;

}
