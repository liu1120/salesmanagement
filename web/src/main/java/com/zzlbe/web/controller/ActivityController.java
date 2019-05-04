package com.zzlbe.web.controller;

import com.zzlbe.core.business.ActivityBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.request.SaleForm;
import com.zzlbe.core.util.DateUtil;
import com.zzlbe.dao.search.SaleSearch;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    // TODO Controller 层尽量不写业务代码，目的是保持 Controller 层清晰，便于维护，阅后即删

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
    @RequestMapping("create")
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
    @RequestMapping("findAllByPage")
    public GenericResponse findAllByPage(@RequestBody SaleSearch saleSearch) {

        return activityBusiness.findAllByPage(saleSearch);
    }

}
