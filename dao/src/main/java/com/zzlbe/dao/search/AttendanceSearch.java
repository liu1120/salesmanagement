package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * PROJECT: Sales
 * DESCRIPTION: 考勤打卡查询
 *
 * @author amos
 * @date 2019/4/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class AttendanceSearch extends BasePageRequest {

    /**
     * 销售员ID
     */
    private Long atSellerId;
    /**
     * 打卡日期
     */
    private String atDay;
    /**
     * 打卡日期根据月份
     */
    private String atDayByMonth;

}
