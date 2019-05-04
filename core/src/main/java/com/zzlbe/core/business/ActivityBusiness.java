package com.zzlbe.core.business;

import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.SaleForm;
import com.zzlbe.dao.search.SaleSearch;

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
     * 分页查询所有活动
     *
     * @param saleSearch SaleSearch
     * @return GenericResponse
     */
    GenericResponse findAllByPage(SaleSearch saleSearch);


}
