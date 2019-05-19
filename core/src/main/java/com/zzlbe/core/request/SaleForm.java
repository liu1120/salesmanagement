package com.zzlbe.core.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SaleForm {

    /**
     * 活动ID
     */
    private Long id;

    /**
     * 销售类型：1正常（默认），2打折，3满减，4满送
     */
    @NotNull(message = "销售类型不能为空")
    private Integer type;
    /**
     * 打折相关：折扣
     */
    @DecimalMin("0")
    @DecimalMax("1")
    private BigDecimal discount;
    /**
     * 0 默认全局sa_area_id不开放；1 销售员申请，sa_area_id启用
     */
    private Integer start;
    /**
     * 满减相关：起价
     */
    @DecimalMin("0")
    private BigDecimal reach;
    /**
     * 满减相关：满减
     */
    @DecimalMin("0")
    private BigDecimal minus;
    /**
     * 开始时间
     */
    @NotBlank(message = "开始时间不能为空")
    private String startTimeStr;
    /**
     * 结束时间
     */
    @NotBlank(message = "结束时间不能为空")
    private String overTimeStr;

    private Date startTime;
    private Date overTime;

    /**
     * 开启活动区域
     */
    private String areaIds;
    /**
     * 活动状态()
     */
    private Integer status;
}
