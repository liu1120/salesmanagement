package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SaleAreaSearch extends BasePageRequest {

    /**
     * 销售类型：1正常（默认），2打折，3满减，4满送
     */
    private Integer id;
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
    private String startTime;
    /**
     * 结束时间
     */
    private String overTime;
    /**
     * 开启活动区域（多个，中间用英文分号[`;`]隔开）
     */
    private String areaIds;
    /**
     * 开启活动区域(单个或表达式)
     */
    private String areaId;
    /**
     * 0默认待审核，1同意，2不同意
     */
    private Integer status;
    /**
     * 销售员ID
     */
    private Long sellerId;
    private String name;
    private String createTime;
    private String updateTime;

}
