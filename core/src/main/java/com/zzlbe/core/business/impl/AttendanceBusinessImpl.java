package com.zzlbe.core.business.impl;

import com.zzlbe.core.business.AttendanceBusiness;
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
import com.zzlbe.dao.page.PageResponse;
import com.zzlbe.dao.search.AttendanceSearch;
import com.zzlbe.dao.search.TripSearch;
import com.zzlbe.dao.search.VacationSearch;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
public class AttendanceBusinessImpl implements AttendanceBusiness {

    /**
     * 上班时间 && 下班时间 && 加班开始时间
     */
    private static final LocalDateTime LOCAL_DATE_START = DateUtil.getLocalDateTimeByHour(8);
    private static final LocalDateTime LOCAL_DATE_END = DateUtil.getLocalDateTimeByHour(17);
    private static final LocalDateTime LOCAL_DATE_OVERTIME = DateUtil.getLocalDateTimeByHour(18);

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
        LocalDateTime punchLocalDateTime = DateUtil.getLocalDateTimeByDate(punchDate);

        Long sellerId = punchForm.getAtSellerId();
        AttendanceSearch attendanceSearch = new AttendanceSearch();
        attendanceSearch.setAtSellerId(sellerId);
        attendanceSearch.setAtDay(DateUtil.getDateStr(punchLocalDateTime));
        AttendanceEntity attendanceEntity = attendanceMapper.selectByExample(attendanceSearch);

        // insert or update
        boolean doInsert = attendanceEntity == null;
        if (doInsert) {
            attendanceEntity = new AttendanceEntity();
            attendanceEntity.setAtSellerId(sellerId);
            attendanceEntity.setAtDay(punchDate);
        }

        if (DateUtil.isMorning(punchLocalDateTime)) {
            // 上班打卡
            attendanceEntity.setAtStart(punchDate);
            if (punchStart(punchLocalDateTime)) {
                attendanceEntity.setAtStartType(AttendanceConstant.PUNCH_TYPE_START_NORMAL);
            } else {
                attendanceEntity.setAtStartType(AttendanceConstant.PUNCH_TYPE_START_LATE);
            }
            attendanceEntity.setAtType(AttendanceConstant.PUNCH_TYPE_NORMAL);
        } else {
            // 下班打卡
            attendanceEntity.setAtEnd(punchDate);
            if (punchEnd(punchLocalDateTime)) {
                attendanceEntity.setAtEndType(AttendanceConstant.PUNCH_TYPE_END_NORMAL);
            } else {
                attendanceEntity.setAtEndType(AttendanceConstant.PUNCH_TYPE_END_EARLY);
            }
            if (attendanceEntity.getAtStart() == null) {
                attendanceEntity.setAtType(AttendanceConstant.PUNCH_TYPE_EXCEPTION);
            } else if (punchOvertime(punchLocalDateTime)) {
                attendanceEntity.setAtType(AttendanceConstant.PUNCH_TYPE_OVERTIME);
            } else {
                attendanceEntity.setAtType(AttendanceConstant.PUNCH_TYPE_NORMAL);
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
        List<AttendanceEntity> attendanceEntities = attendanceMapper.selectByPage(attendanceSearch);
        Integer total = attendanceMapper.selectByPageTotal(attendanceSearch);

        return new GenericResponse<>(getPageResponseByEntities(attendanceEntities, total));
    }

    @Override
    public GenericResponse trip(AttendanceTripForm punchForm) {
        TripEntity tripEntity = new TripEntity();
        BeanUtils.copyProperties(punchForm, tripEntity);
        tripEntity.setTrState(1);
        tripMapper.insert(tripEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse tripList(TripSearch tripSearch) {
        List<TripEntity> tripEntities = tripMapper.selectByPage(tripSearch);
        Integer total = tripMapper.selectByPageTotal(tripSearch);

        return new GenericResponse<>(getPageResponseByEntities(tripEntities, total));
    }

    @Override
    public GenericResponse tripVerify(AttendanceTripForm tripForm) {
        TripEntity tripEntity = tripMapper.selectById(tripForm.getTrId());
        if (tripEntity == null) {
            return GenericResponse.SUCCESS;
        }

        tripEntity.setTrState(tripForm.getTrState());
        tripEntity.setTrReason(tripForm.getTrReason());
        tripMapper.update(tripEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse vacation(AttendanceVacationForm punchForm) {
        VacationEntity vacationEntity = new VacationEntity();
        BeanUtils.copyProperties(punchForm, vacationEntity);
        vacationEntity.setVaStatus(0);
        vacationMapper.insert(vacationEntity);

        return GenericResponse.SUCCESS;
    }

    @Override
    public GenericResponse vacationList(VacationSearch vacationSearch) {
        List<VacationEntity> vacationEntities = vacationMapper.selectByPage(vacationSearch);
        Integer total = vacationMapper.selectByPageTotal(vacationSearch);

        return new GenericResponse<>(getPageResponseByEntities(vacationEntities, total));
    }

    private <T> PageResponse<T> getPageResponseByEntities(List<T> entities, Integer total) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setSize(entities.size());
        pageResponse.setList(entities);
        pageResponse.setTotal(total);
        return pageResponse;
    }

    @Override
    public GenericResponse vacationVerify(AttendanceVacationForm punchForm) {
        VacationEntity vacationEntity = vacationMapper.selectById(punchForm.getVaId());
        if (vacationEntity == null) {
            return GenericResponse.SUCCESS;
        }

        vacationEntity.setVaType(punchForm.getVaType());
        vacationEntity.setVaStatus(punchForm.getVaStatus());
        vacationEntity.setVaNotes(punchForm.getVaNotes());
        vacationMapper.update(vacationEntity);

        return GenericResponse.SUCCESS;
    }

    /**
     * 正常上班时间 ? true : false
     */
    private static boolean punchStart(LocalDateTime localDateTime) {
        return localDateTime.isBefore(LOCAL_DATE_START);
    }

    /**
     * 正常下班时间 ? true : false
     */
    private static boolean punchEnd(LocalDateTime localDateTime) {
        return localDateTime.isAfter(LOCAL_DATE_END);
    }

    /**
     * 加班 ? true : false
     */
    private static boolean punchOvertime(LocalDateTime localDateTime) {
        return localDateTime.isAfter(LOCAL_DATE_OVERTIME);
    }

}