package com.zzlbe.core.business.impl;

import com.zzlbe.core.business.ActivityBusiness;
import com.zzlbe.core.common.ErrorCodeEnum;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.SaleForm;
import com.zzlbe.dao.entity.SaleEntity;
import com.zzlbe.dao.mapper.SaleMapper;
import com.zzlbe.dao.search.SaleSearch;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 活动相关业务实现
 *
 * @author amos
 * @date 2019/5/4
 */
@Component("activityBusiness")
public class ActivityBusinessImpl extends BaseBusinessImpl implements ActivityBusiness {

    @Resource
    private SaleMapper saleMapper;


    @Override
    public GenericResponse create(SaleForm saleForm) {
        SaleSearch saleSearch = new SaleSearch();

        if (saleForm.getType().equals(2)) {
            // 打折活动
            if (saleForm.getDiscount() == null) {
                return new GenericResponse(ErrorCodeEnum.ACTIVITY_DISCOUNT_NOT_NULL);
            }
            saleSearch.setDiscount(saleForm.getDiscount());
        } else if (saleForm.getType().equals(3)) {
            // 满减活动
            if (saleForm.getReach() == null || saleForm.getMinus() == null) {
                return new GenericResponse(ErrorCodeEnum.ACTIVITY_MINUS_NOT_NULL);
            }
            saleSearch.setReach(saleForm.getReach());
            saleSearch.setMinus(saleForm.getMinus());
        }

        // 清除无效字段的值
        clearSaleEntity(saleForm);

        saleSearch.setType(saleForm.getType());
        saleSearch.setStartTime(saleForm.getStartTime());
        saleSearch.setOverTime(saleForm.getOverTime());
        List<SaleEntity> saleEntities = saleMapper.selectByPage(saleSearch);
        if (saleEntities.size() > 0) {
            return new GenericResponse(ErrorCodeEnum.ACTIVITY_EXIST);
        }

        SaleEntity saleEntity = new SaleEntity();
        BeanUtils.copyProperties(saleForm, saleEntity);
        saleEntity.setStart(saleEntity.getStart() == null ? 0 : saleEntity.getStart());
        saleEntity.setStatus(true);
        saleEntity.setCreateTime(new Date());

        saleMapper.insert(saleEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse findAllByPage(SaleSearch saleSearch) {

        List<SaleEntity> orderEntities = saleMapper.selectByPage(saleSearch);
        Integer total = saleMapper.selectByPageTotal(saleSearch);

        return genericPageResponse(orderEntities, total);
    }

    private void clearSaleEntity(SaleForm saleForm) {
        Integer type = saleForm.getType();
        if (type == 1) {
            saleForm.setDiscount(null);
            saleForm.setReach(null);
            saleForm.setMinus(null);
        } else if (type == 2) {
            saleForm.setReach(null);
            saleForm.setMinus(null);
        } else if (type == 3) {
            saleForm.setDiscount(null);
        }
    }

}