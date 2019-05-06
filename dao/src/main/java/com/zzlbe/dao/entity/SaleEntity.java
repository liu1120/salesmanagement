package com.zzlbe.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SaleEntity {

    private Long id;
    /**
     * 销售类型：1正常（默认），2打折，3满减，4满送
     */
    private Integer type;
    /**
     * 打折相关：折扣
     */
    private BigDecimal discount;
    /**
     * 0 默认全局sa_area_id不开放；
     * 1 销售员申请，sa_area_id启用
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
     * 开启活动区域
     */
    private String areaIds;
    /**
     * 0默认待审核，1同意，2不同意
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
