package com.zzlbe.web.controller;

import com.zzlbe.core.business.AttendanceBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.AttendancePunchForm;
import com.zzlbe.core.request.AttendanceTripForm;
import com.zzlbe.core.request.AttendanceVacationForm;
import com.zzlbe.core.util.DateUtil;
import com.zzlbe.dao.search.AttendanceSearch;
import com.zzlbe.dao.search.TripSearch;
import com.zzlbe.dao.search.VacationSearch;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * PROJECT: Sales
 * DESCRIPTION: 打卡/出差/请假
 *
 * @author duGraceful
 * @date 2019/4/2
 */
@RestController
@RequestMapping("attendance")
@CrossOrigin("http://localhost:8080")
public class AttendanceController {

    @Resource
    private AttendanceBusiness attendanceBusiness;


    /**
     * 打卡
     */
    @RequestMapping("punch")
    public GenericResponse punch(@RequestBody AttendancePunchForm punchForm) {

        return attendanceBusiness.punch(punchForm);
    }

    /**
     * 打卡
     */
    @RequestMapping("punchList")
    public GenericResponse punchList(@RequestBody AttendanceSearch attendanceSearch) {

        return attendanceBusiness.punchList(attendanceSearch);
    }

    /**
     * 出差
     */
    @RequestMapping("trip")
    public GenericResponse trip(@RequestBody AttendanceTripForm tripForm) {
        tripForm.setTrStart(DateUtil.getDateByStr(tripForm.getTrStartStr()));
        tripForm.setTrStop(DateUtil.getDateByStr(tripForm.getTrStopStr()));

        return attendanceBusiness.trip(tripForm);
    }

    /**
     * 出差
     */
    @RequestMapping("tripList")
    public GenericResponse tripList(@RequestBody TripSearch tripSearch) {

        return attendanceBusiness.tripList(tripSearch);
    }

    /**
     * 请假详情
     */
    @GetMapping("tripDetail/{id}")
    public GenericResponse tripDetail(@PathVariable("id") Long id) {

        return attendanceBusiness.tripDetail(id);
    }

    /**
     * 出差审批
     */
    @RequestMapping("tripVerify")
    public GenericResponse tripVerify(@RequestBody AttendanceTripForm tripForm) {

        return attendanceBusiness.tripVerify(tripForm);
    }

    /**
     * 编辑出差（仅限待审核状态）
     */
    @RequestMapping("tripModify")
    public GenericResponse tripModify(@RequestBody AttendanceTripForm tripForm) {
        tripForm.setTrStart(DateUtil.getDateByStr(tripForm.getTrStartStr()));
        tripForm.setTrStop(DateUtil.getDateByStr(tripForm.getTrStopStr()));

        return attendanceBusiness.tripModify(tripForm);
    }

    /**
     * 删除出差（仅限待审核状态）
     * （暂不做）出差未结束，提前回来上班了，此时就要删除出差了
     */
    @RequestMapping("tripRemove")
    public GenericResponse tripRemove(@RequestBody AttendanceTripForm tripForm) {

        return attendanceBusiness.tripRemove(tripForm);
    }

    /**
     * 请假
     */
    @RequestMapping("vacation")
    public GenericResponse vacation(@RequestBody AttendanceVacationForm vacationForm) {
        vacationForm.setVaStart(DateUtil.getDateByStr(vacationForm.getVaStartStr()));
        vacationForm.setVaEnd(DateUtil.getDateByStr(vacationForm.getVaEndStr()));

        return attendanceBusiness.vacation(vacationForm);
    }

    /**
     * 请假
     */
    @RequestMapping("vacationList")
    public GenericResponse vacationList(@RequestBody VacationSearch vacationSearch) {

        return attendanceBusiness.vacationList(vacationSearch);
    }

    /**
     * 请假详情
     */
    @GetMapping("vacationDetail/{id}")
    public GenericResponse vacationDetail(@PathVariable("id") Long id) {

        return attendanceBusiness.vacationDetail(id);
    }

    /**
     * 请假审批
     */
    @RequestMapping("vacationVerify")
    public GenericResponse vacationVerify(@RequestBody AttendanceVacationForm vacationForm) {

        return attendanceBusiness.vacationVerify(vacationForm);
    }

    /**
     * 编辑请假（仅限待审核状态）
     */
    @RequestMapping("vacationModify")
    public GenericResponse vacationModify(@RequestBody AttendanceVacationForm vacationForm) {
        vacationForm.setVaStart(DateUtil.getDateByStr(vacationForm.getVaStartStr()));
        vacationForm.setVaEnd(DateUtil.getDateByStr(vacationForm.getVaEndStr()));

        return attendanceBusiness.vacationModify(vacationForm);
    }

    /**
     * 删除请假（仅限待审核状态）
     * （暂不做）假期未结束，提前回来上班了，此时就要删除请假了
     */
    @RequestMapping("vacationRemove")
    public GenericResponse vacationRemove(@RequestBody AttendanceVacationForm vacationForm) {

        return attendanceBusiness.vacationRemove(vacationForm);
    }

}
