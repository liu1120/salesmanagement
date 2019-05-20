package com.zzlbe.core.business.impl;

import com.zzlbe.core.business.AttendanceBusiness;
import com.zzlbe.core.common.ErrorCodeEnum;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.constant.AttendanceConstant;
import com.zzlbe.core.request.AttendancePunchForm;
import com.zzlbe.core.request.AttendanceTripForm;
import com.zzlbe.core.request.AttendanceVacationForm;
import com.zzlbe.core.util.DateUtil;
import com.zzlbe.dao.entity.AttendanceEntity;
import com.zzlbe.dao.entity.TripEntity;
import com.zzlbe.dao.entity.VacationEntity;
import com.zzlbe.dao.mapper.AttendanceMapper;
import com.zzlbe.dao.mapper.TripMapper;
import com.zzlbe.dao.mapper.VacationMapper;
import com.zzlbe.dao.search.AttendanceSearch;
import com.zzlbe.dao.search.TripSearch;
import com.zzlbe.dao.search.VacationSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 打卡/出差/请假
 *
 * @author amos
 * @date 2019/4/14
 */
@Component("attendanceBusiness")
public class AttendanceBusinessImpl extends BaseBusinessImpl implements AttendanceBusiness {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceBusinessImpl.class);

    @Resource
    private AttendanceMapper attendanceMapper;
    @Resource
    private TripMapper tripMapper;
    @Resource
    private VacationMapper vacationMapper;

    @Override
    public GenericResponse punch(AttendancePunchForm punchForm) {
        // 两种格式的当前时间
        Date punchDate = new Date();

        Long sellerId = punchForm.getAtSellerId();
        AttendanceSearch attendanceSearch = new AttendanceSearch();
        attendanceSearch.setAtSellerId(sellerId);
        attendanceSearch.setAtDay(DateUtil.getDateStr(punchDate));
        AttendanceEntity attendanceEntity = attendanceMapper.selectByExample(attendanceSearch);

        LOGGER.info(">>>>>>>>>>>>>>>> [{} 打卡] Date: {}", sellerId, punchDate);

        // insert or update
        boolean doInsert = attendanceEntity == null;
        if (doInsert) {
            attendanceEntity = new AttendanceEntity();
            attendanceEntity.setAtSellerId(sellerId);
            attendanceEntity.setAtDay(punchDate);
        }

        if (DateUtil.isMorning(punchDate)) {
            // 上班打卡
            attendanceEntity.setAtStart(punchDate);
            if (punchStart(punchDate)) {
                attendanceEntity.setAtStartType(AttendanceConstant.PUNCH_TYPE_START_NORMAL);
                LOGGER.info(">>>>>>>>>>>>>>>> {} 上班打卡 >>>>> [正常]", sellerId);
            } else {
                attendanceEntity.setAtStartType(AttendanceConstant.PUNCH_TYPE_START_LATE);
                LOGGER.info(">>>>>>>>>>>>>>>> {} 上班打卡 >>>>> [迟到]", sellerId);
            }
            attendanceEntity.setAtType(AttendanceConstant.PUNCH_TYPE_NORMAL);
            LOGGER.info(">>>>>>>>>>>>>>>> {} 上班打卡 >>>>> [今天打卡状态：正常打卡]", sellerId);
        } else {
            // 下班打卡
            attendanceEntity.setAtEnd(punchDate);
            if (punchEnd(punchDate)) {
                attendanceEntity.setAtEndType(AttendanceConstant.PUNCH_TYPE_END_NORMAL);
                LOGGER.info(">>>>>>>>>>>>>>>> {} 下班打卡 >>>>> [正常]", sellerId);
            } else {
                attendanceEntity.setAtEndType(AttendanceConstant.PUNCH_TYPE_END_EARLY);
                LOGGER.info(">>>>>>>>>>>>>>>> {} 下班打卡 >>>>> [早退]", sellerId);
            }
            if (attendanceEntity.getAtStart() == null) {
                attendanceEntity.setAtType(AttendanceConstant.PUNCH_TYPE_EXCEPTION);
                LOGGER.info(">>>>>>>>>>>>>>>> {} 下班打卡 >>>>> [今天打卡状态：异常, 未签入]", sellerId);
            } else if (punchOvertime(punchDate)) {
                attendanceEntity.setAtType(AttendanceConstant.PUNCH_TYPE_OVERTIME);
                LOGGER.info(">>>>>>>>>>>>>>>> {} 下班打卡 >>>>> [今天打卡状态：正常, 加班]", sellerId);
            } else {
                attendanceEntity.setAtType(AttendanceConstant.PUNCH_TYPE_NORMAL);
                LOGGER.info(">>>>>>>>>>>>>>>> {} 下班打卡 >>>>> [今天打卡状态：正常]", sellerId);
            }
        }

        if (doInsert) {
            attendanceMapper.insert(attendanceEntity);
        } else {
            attendanceMapper.update(attendanceEntity);
        }

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse punchList(AttendanceSearch attendanceSearch) {
        List<AttendanceEntity> entities = attendanceMapper.selectByPage(attendanceSearch);
        Integer total = attendanceMapper.selectByPageTotal(attendanceSearch);

        return genericPageResponse(entities, total);
    }


    @Override
    public GenericResponse trip(AttendanceTripForm form) {
        TripEntity tripEntity = new TripEntity();
        BeanUtils.copyProperties(form, tripEntity);
        tripEntity.setTrState(1);
        tripMapper.insert(tripEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse tripList(TripSearch tripSearch) {
        List<TripEntity> entities = tripMapper.selectByPage(tripSearch);
        Integer total = tripMapper.selectByPageTotal(tripSearch);

        return genericPageResponse(entities, total);
    }

    @Override
    public GenericResponse tripVerify(AttendanceTripForm tripForm) {
        TripEntity tripEntity = tripMapper.selectById(tripForm.getTrId());
        if (tripEntity == null) {
            return GenericResponse.ERROR;
        }

        tripEntity.setTrState(tripForm.getTrState());
        tripEntity.setTrReason(tripForm.getTrReason());
        tripMapper.update(tripEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse tripModify(AttendanceTripForm tripForm) {
        TripEntity tripEntity = tripMapper.selectById(tripForm.getTrId());
        if (tripEntity == null) {
            return GenericResponse.ERROR;
        }
        if (tripEntity.getTrState() != 1) {
            return new GenericResponse(ErrorCodeEnum.ATTENDANCE_MODIFY_CLOSE);
        }

        tripEntity.setTrCity(tripForm.getTrCity());
        tripEntity.setTrToCity(tripForm.getTrToCity());
        tripEntity.setTrType(tripForm.getTrType());
        tripEntity.setTrIntent(tripForm.getTrIntent());
        tripEntity.setTrStart(tripForm.getTrStart());
        tripEntity.setTrStop(tripForm.getTrStop());
        tripMapper.update(tripEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse tripRemove(AttendanceTripForm tripForm) {
        TripEntity tripEntity = tripMapper.selectById(tripForm.getTrId());
        if (tripEntity == null) {
            return GenericResponse.ERROR;
        }
        if (tripEntity.getTrState() != 1) {
            return new GenericResponse(ErrorCodeEnum.ATTENDANCE_MODIFY_CLOSE);
        }

        tripMapper.delete(tripForm.getTrId());

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse vacation(AttendanceVacationForm form) {
        VacationEntity vacationEntity = new VacationEntity();
        BeanUtils.copyProperties(form, vacationEntity);
        vacationEntity.setVaStatus(0);
        vacationMapper.insert(vacationEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse vacationList(VacationSearch vacationSearch) {
        List<VacationEntity> entities = vacationMapper.selectByPage(vacationSearch);
        Integer total = vacationMapper.selectByPageTotal(vacationSearch);

        return genericPageResponse(entities, total);
    }

    @Override
    public GenericResponse vacationVerify(AttendanceVacationForm form) {
        VacationEntity vacationEntity = vacationMapper.selectById(form.getVaId());
        if (vacationEntity == null) {
            return GenericResponse.ERROR;
        }

        vacationEntity.setVaType(form.getVaType());
        vacationEntity.setVaStatus(form.getVaStatus());
        vacationEntity.setVaNotes(form.getVaNotes());
        vacationMapper.update(vacationEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse vacationModify(AttendanceVacationForm form) {
        VacationEntity vacationEntity = vacationMapper.selectById(form.getVaId());
        if (vacationEntity == null) {
            return GenericResponse.ERROR;
        }
        if (vacationEntity.getVaStatus() != 0) {
            return new GenericResponse(ErrorCodeEnum.ATTENDANCE_MODIFY_CLOSE);
        }

        vacationEntity.setVaStart(form.getVaStart());
        vacationEntity.setVaEnd(form.getVaEnd());
        vacationEntity.setVaDays(form.getVaDays());
        vacationEntity.setVaReason(form.getVaReason());
        vacationEntity.setVaType(form.getVaType());
        vacationMapper.update(vacationEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse vacationRemove(AttendanceVacationForm form) {
        VacationEntity vacationEntity = vacationMapper.selectById(form.getVaId());
        if (vacationEntity == null) {
            return GenericResponse.ERROR;
        }
        if (vacationEntity.getVaStatus() != 0) {
            return new GenericResponse(ErrorCodeEnum.ATTENDANCE_MODIFY_CLOSE);
        }

        vacationMapper.delete(form.getVaId());

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse tripDetail(Long id) {

        return new GenericResponse<>(tripMapper.selectById(id));
    }

    @Override
    public GenericResponse vacationDetail(Long id) {

        return new GenericResponse<>(vacationMapper.selectById(id));
    }

    /**
     * 正常上班时间 ? true : false
     */
    private static boolean punchStart(Date date) {
        return date.before(DateUtil.getDateByHour(8));
    }

    /**
     * 正常下班时间 ? true : false
     */
    private static boolean punchEnd(Date date) {
        return date.after(DateUtil.getDateByHour(17));
    }

    /**
     * 加班 ? true : false
     */
    private static boolean punchOvertime(Date date) {
        return date.after(DateUtil.getDateByHour(18));
    }

}
