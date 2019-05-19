package com.zzlbe.core.business.impl;

import com.zzlbe.core.business.ActivityBusiness;
import com.zzlbe.core.common.ErrorCodeEnum;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.SaleCheckForm;
import com.zzlbe.core.request.SaleForm;
import com.zzlbe.core.util.DateUtil;
import com.zzlbe.dao.entity.AreaEntity;
import com.zzlbe.dao.entity.SaleEntity;
import com.zzlbe.dao.mapper.AreaMapper;
import com.zzlbe.dao.mapper.SaleMapper;
import com.zzlbe.dao.search.SaleSearch;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    @Resource
    private AreaMapper areaMapper;


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
        // 默认全局有效
        saleEntity.setStart(saleEntity.getStart() == null ? 0 : saleEntity.getStart());
        // 管理员创建不用审核
        saleEntity.setStatus(saleEntity.getStart() == 0 ? 1 : 0);
        saleEntity.setCreateTime(new Date());

        saleMapper.insert(saleEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse check(SaleCheckForm saleCheckForm) {
        SaleEntity saleEntity = saleMapper.selectById(saleCheckForm.getSaleId());
        if (saleEntity == null) {
            return new GenericResponse(ErrorCodeEnum.ACTIVITY_NOT_EXIST);
        }
        if (saleEntity.getStatus() != 0) {
            return new GenericResponse(ErrorCodeEnum.ACTIVITY_CHECKED);
        }
        saleEntity.setStatus(saleCheckForm.getStatus());

        saleMapper.update(saleEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse update(SaleForm saleForm) {
        if (saleForm.getId() == null) {
            return new GenericResponse(ErrorCodeEnum.ACTIVITY_EXIST);
        }
        if (saleForm.getType().equals(2)) {
            // 打折活动
            if (saleForm.getDiscount() == null) {
                return new GenericResponse(ErrorCodeEnum.ACTIVITY_DISCOUNT_NOT_NULL);
            }
        } else if (saleForm.getType().equals(3)) {
            // 满减活动
            if (saleForm.getReach() == null || saleForm.getMinus() == null) {
                return new GenericResponse(ErrorCodeEnum.ACTIVITY_MINUS_NOT_NULL);
            }
        }

        // 清除无效字段的值
        clearSaleEntity(saleForm);

        SaleEntity saleEntity = saleMapper.selectById(saleForm.getId());
        if (saleEntity == null) {
            return new GenericResponse(ErrorCodeEnum.ACTIVITY_EXIST);
        }
        // 销售员申请的，审核通过的不允许修改
        if (saleEntity.getStart() == 1 && saleEntity.getStatus() != 0) {
            return new GenericResponse(ErrorCodeEnum.ACTIVITY_NOT_MODIFY);
        }

        BeanUtils.copyProperties(saleForm, saleEntity);
        // 默认全局有效
        saleEntity.setStart(saleEntity.getStart() == null ? 0 : saleEntity.getStart());
        // 管理员创建不用审核
        saleEntity.setStatus(saleEntity.getStart() == 0 ? 1 : 0);
        saleEntity.setUpdateTime(new Date());

        saleMapper.update(saleEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse delete(SaleForm saleForm) {
        if (saleForm.getId() == null) {
            return new GenericResponse(ErrorCodeEnum.ACTIVITY_EXIST);
        }
        SaleEntity saleEntity = saleMapper.selectById(saleForm.getId());
        if (saleEntity == null) {
            return new GenericResponse(ErrorCodeEnum.ACTIVITY_EXIST);
        }
        // 销售员申请的，审核通过的不允许修改
        if (saleEntity.getStart() == 1 && saleEntity.getStatus() != 0) {
            return new GenericResponse(ErrorCodeEnum.ACTIVITY_NOT_DELETE);
        }

        saleEntity.setStatus(3);

        saleMapper.update(saleEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse get(Long saleId) {
        if (saleId == null) {
            return new GenericResponse(ErrorCodeEnum.ACTIVITY_EXIST);
        }

        return new GenericResponse<>(saleMapper.selectById(saleId));
    }

    @Override
    public GenericResponse findAllByPage(SaleSearch saleSearch) {
        // 销售员编号
        Long sellerId = saleSearch.getSellerId();
        if (sellerId != null) {
            Set<Long> areaIds = new TreeSet<>();
            List<AreaEntity> areaEntities = areaMapper.selectBySpid(sellerId);
            areaEntities.forEach(areaEntity -> areaIds.add(areaEntity.getCountycode()));
            if (areaIds.size() > 0) {
                // 0默认全局sa_area_id不开放；1销售员申请，sa_area_id启用
                StringBuilder sb = new StringBuilder();
                areaIds.forEach(areaId -> sb.append(areaId).append("|"));
                String ids = sb.toString();
                ids = ids.substring(0, ids.length() - 1);
                saleSearch.setAreaId(ids);
            }
        }

        List<SaleEntity> orderEntities = saleMapper.selectByPage(saleSearch);
        Integer total = saleMapper.selectByPageTotal(saleSearch);

        return genericPageResponse(orderEntities, total);
    }

    @Override
    public GenericResponse findAllByCounty(Long countyCode) {
        // 只查询活动状态 status=1 && 区域活动 areaIds 包含 countyCode && 公司活动 areaIds 不包含 countyCode 的活动
        List<SaleEntity> saleEntities = saleMapper.selectByCounty(countyCode);

        return new GenericResponse<>(saleEntities);
    }

    @Override
    public GenericResponse<BigDecimal> joinActivity(BigDecimal totalAmount, Long id) {
        SaleEntity saleEntity = saleMapper.selectById(id);
        if (saleEntity == null) {
            return new GenericResponse<>(ErrorCodeEnum.ACTIVITY_NOT_EXIST);
        }
        // 判断活动是否审核通过
        if (saleEntity.getStatus() != 1) {
            return new GenericResponse<>(ErrorCodeEnum.ACTIVITY_INVALID);
        }
        Date date = new Date();
        // 判断开始时间
        if (DateUtil.before(date, saleEntity.getStartTime())) {
            return new GenericResponse<>(ErrorCodeEnum.ACTIVITY_NOT_START);
        }
        // 判断结束时间
        if (DateUtil.after(date, saleEntity.getOverTime())) {
            return new GenericResponse<>(ErrorCodeEnum.ACTIVITY_EXPIRED);
        }

        BigDecimal amount = totalAmount;
        // 销售类型：1正常（默认），2打折，3满减，4满送
        Integer type = saleEntity.getType();
        if (type == 2) {
            amount = totalAmount.multiply(saleEntity.getDiscount());
        } else if (type == 3) {
            BigDecimal divide = totalAmount.divide(saleEntity.getReach(), 0, BigDecimal.ROUND_DOWN);
            if (BigDecimal.ZERO.compareTo(divide) < 0) {
                BigDecimal multiply = divide.multiply(saleEntity.getMinus());
                amount = totalAmount.subtract(multiply);
            }
        }

        return new GenericResponse<>(amount);
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
