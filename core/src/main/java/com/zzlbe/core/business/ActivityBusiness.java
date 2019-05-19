package com.zzlbe.core.business;

import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.SaleCheckForm;
import com.zzlbe.core.request.SaleForm;
import com.zzlbe.dao.search.SaleSearch;

import java.math.BigDecimal;

/**
 * PROJECT: Sales
 * DESCRIPTION: 活动相关业务
 *
 * @author amos
 * @date 2019/5/4
 */
public interface ActivityBusiness {

    /**
     * 创建活动
     *
     * @param saleForm 活动相关表单
     * @return GenericResponse
     */
    GenericResponse create(SaleForm saleForm);

    /**
     * 审核活动
     *
     * @param saleCheckForm 审核活动相关表单
     * @return GenericResponse
     */
    GenericResponse check(SaleCheckForm saleCheckForm);

    /**
     * 更新
     *
     * @param saleForm 活动相关表单
     * @return GenericResponse
     */
    GenericResponse update(SaleForm saleForm);

    /**
     * 删除
     *
     * @param saleForm 活动相关表单
     * @return GenericResponse
     */
    GenericResponse delete(SaleForm saleForm);

    /**
     * 获取
     *
     * @param saleId 活动编号
     * @return GenericResponse
     */
    GenericResponse get(Long saleId);

    /**
     * 分页查询所有活动
     *
     * @param saleSearch SaleSearch
     * @return GenericResponse
     */
    GenericResponse findAllByPage(SaleSearch saleSearch);

    /**
     * 根据 县区ID 查询所有可参加的所有活动
     *
     * @param countyCode 县区ID
     * @return GenericResponse
     */
    GenericResponse findAllByCounty(Long countyCode);

    /**
     * 参加活动 （使用者注意：此处并没有校验用户是否可参加本活动）
     *
     * @param totalAmount 订单金额
     * @param id          活动ID
     * @return 活动有效则返回活动后的价格，反之返回失败原因
     */
    GenericResponse<BigDecimal> joinActivity(BigDecimal totalAmount, Long id);

}
