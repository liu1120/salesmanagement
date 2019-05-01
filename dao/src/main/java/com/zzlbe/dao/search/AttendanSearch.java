package com.zzlbe.dao.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class AttendanSearch extends BasePageRequest {//比AttendanceEntity新增一列name


    private Long atId;


    /**
     * 销售员ID
     */
    private Long atSellerId;

    private String name;

    /**
     * 打卡日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private String atDay;
    /**
     * 1正常上班，2异常打卡，3加班，4请假，5出差
     */
    private Integer atType;
    /**
     * 上班打卡时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm:ss")
    private String atStart;
    /**
     * 上班打卡类型：1正常,2迟到,3未签到。默认未签到
     */
    private Integer atStartType;
    /**
     * 下班打卡时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm:ss")
    private String atEnd;
    /**
     * 下班打卡类型：1正常,2迟到,3未签到。默认未签到
     */
    private Integer atEndType;

}
