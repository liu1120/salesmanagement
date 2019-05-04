package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SaleSearch extends BasePageRequest {

    /**
     * 销售类型：1正常（默认），2打折，3满减，4满送
     */
    private Integer type;
    /**
     * 打折相关：折扣
     */
    private BigDecimal discount;
    /**
     * 0默认全局sa_area_id不开放；1销售员申请，sa_area_id启用
     */
    private Integer start;
    /**
     * 满减相关：起价
     */
    private BigDecimal reach;
    /**
     * 满减相关：满减
     */
    private BigDecimal minus;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date overTime;
    /**
     * 开启活动区域（多个，中间用英文分号[`;`]隔开）
     */
    private String areaIds;
    /**
     * 开启活动区域(单个)
     */
    private String areaId;
    /**
     * 0默认同意，1不同意
     */
    private Boolean status;

}