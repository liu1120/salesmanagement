package com.zzlbe.web.controller;

import com.zzlbe.core.business.ActivityBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.SaleCheckForm;
import com.zzlbe.core.request.SaleForm;
import com.zzlbe.core.util.DateUtil;
import com.zzlbe.dao.search.SaleSearch;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author liushuai
 */
@Controller
@RequestMapping("activity")
public class ActivityController {

    @Resource
    private ActivityBusiness activityBusiness;

    /**
     * 1、创建活动（管理员创建start=0/销售员创建start=1）
     * 2、审核活动（活动状态status=0的才需要审核，1为通过，2为拒绝）
     * 3、传入销售员ID，获取其辖区内所有可参加的活动
     *
     * 传入活动编号，传入总消费金额，计算活动最终金额，以及满送
     * 查询用户能参加的活动
     */

    /**
     * 促销活动
     */
    @GetMapping("/")
    public ModelAndView activity() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/ac_list");
        return mv;
    }


    @ResponseBody
    @PostMapping("create")
    public GenericResponse create(@RequestBody @Valid SaleForm saleForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            // bindingResult.getAllErrors().get(0) 获取Form中检测的
            return GenericResponse.ERROR.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        saleForm.setStartTime(DateUtil.getDateByStr(saleForm.getStartTimeStr()));
        saleForm.setOverTime(DateUtil.getDateByStr(saleForm.getOverTimeStr()));

        return activityBusiness.create(saleForm);
    }

    @ResponseBody
    @PostMapping("check")
    public GenericResponse check(@RequestBody @Valid SaleCheckForm saleCheckForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            return GenericResponse.ERROR.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        return activityBusiness.check(saleCheckForm);
    }

    @ResponseBody
    @PostMapping("update")
    public GenericResponse update(@RequestBody @Valid SaleForm saleForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            // bindingResult.getAllErrors().get(0) 获取Form中检测的
            return GenericResponse.ERROR.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        saleForm.setStartTime(DateUtil.getDateByStr(saleForm.getStartTimeStr()));
        saleForm.setOverTime(DateUtil.getDateByStr(saleForm.getOverTimeStr()));

        return activityBusiness.update(saleForm);
    }

    @ResponseBody
    @PostMapping("delete")
    public GenericResponse delete(@RequestBody SaleForm saleForm) {

        return activityBusiness.delete(saleForm);
    }

    @ResponseBody
    @GetMapping("get/{saleId}")
    public GenericResponse get(@PathVariable("saleId") Long saleId) {

        return activityBusiness.get(saleId);
    }

    @ResponseBody
    @PostMapping("findAllByPage")
    public GenericResponse findAllByPage(@RequestBody SaleSearch saleSearch) {

        return activityBusiness.findAllByPage(saleSearch);
    }

    @ResponseBody
    @GetMapping("findAllByCounty/{countyCode}")
    public GenericResponse findAllByCounty(@PathVariable("countyCode") Long countyCode) {
        if (countyCode == null) {
            return GenericResponse.ERROR;
        }

        return activityBusiness.findAllByCounty(countyCode);
    }

}
