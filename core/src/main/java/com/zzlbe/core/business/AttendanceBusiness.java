package com.zzlbe.core.business;

import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.AttendancePunchForm;
import com.zzlbe.core.request.AttendanceTripForm;
import com.zzlbe.core.request.AttendanceVacationForm;
import com.zzlbe.dao.search.AttendanceSearch;
import com.zzlbe.dao.search.TripSearch;
import com.zzlbe.dao.search.VacationSearch;

/**
 * PROJECT: Sales
 * DESCRIPTION: 打卡/出差/请假
 *
 * @author amos
 * @date 2019/4/14
 */
public interface AttendanceBusiness {

    /**
     * 打卡
     */
    GenericResponse punch(AttendancePunchForm form);

    /**
     * 个人打卡列表
     */
    GenericResponse punchList(AttendanceSearch attendanceSearch);


    /**
     * 出差
     */
    GenericResponse trip(AttendanceTripForm form);

    /**
     * 出差列表
     */
    GenericResponse tripList(TripSearch tripSearch);

    /**
     * 出差审批
     */
    GenericResponse tripVerify(AttendanceTripForm form);

    /**
     * 编辑出差（仅限待审核状态）
     */
    GenericResponse tripModify(AttendanceTripForm form);

    /**
     * 删除出差（仅限待审核状态）
     */
    GenericResponse tripRemove(AttendanceTripForm form);


    /**
     * 请假
     */
    GenericResponse vacation(AttendanceVacationForm form);

    /**
     * 请假列表
     */
    GenericResponse vacationList(VacationSearch vacationSearch);

    /**
     * 请假审批
     */
    GenericResponse vacationVerify(AttendanceVacationForm form);

    /**
     * 编辑请假（仅限待审核状态）
     */
    GenericResponse vacationModify(AttendanceVacationForm form);

    /**
     * 删除请假（仅限待审核状态）
     */
    GenericResponse vacationRemove(AttendanceVacationForm form);

}
