package com.zzlbe.core.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * PROJECT: Sales
 * DESCRIPTION: 活动审核表单
 *
 * @author amos
 * @date 2019/5/8
 */
@Data
public class SaleCheckForm {

    /**
     * 活动编号
     */
    @NotNull(message = "活动编号不能为空")
    private Long saleId;

    /**
     * 1同意，2不同意
     */
    @NotNull(message = "审核结果不能为空")
    @Min(value = 1, message = "审核结果无效")
    @Max(value = 2, message = "审核结果无效")
    private Integer status;

}
