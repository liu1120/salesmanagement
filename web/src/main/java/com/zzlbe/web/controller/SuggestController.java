package com.zzlbe.web.controller;

import com.zzlbe.core.business.SuggestBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.dao.entity.SuggestEntity;
import com.zzlbe.dao.entity.SuggestTopicEntity;
import com.zzlbe.dao.search.SuggestSearch;
import com.zzlbe.dao.search.SuggestTopicSearch;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 投诉建议技术咨询
 *
 * @author amos
 * @date 2019/4/21
 */
@RestController
@RequestMapping("suggest")
public class SuggestController {

    @Resource
    private SuggestBusiness suggestBusiness;

    /**
     * 创建 [投诉/建议/技术咨询]
     */
    @PostMapping("create")
    public GenericResponse create(@RequestBody @Valid SuggestEntity suggestEntity, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return GenericResponse.ERROR.setMessage(list.get(0).getDefaultMessage());
        }

        return suggestBusiness.create(suggestEntity);
    }

    /**
     * 创建回复 [投诉/建议/技术咨询]
     */
    @PostMapping("reply")
    public GenericResponse reply(@RequestBody @Valid SuggestTopicEntity suggestTopicEntity, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return GenericResponse.ERROR.setMessage(list.get(0).getDefaultMessage());
        }

        return suggestBusiness.reply(suggestTopicEntity);
    }

    /**
     * 创建回复 [投诉/建议/技术咨询]
     */
    @GetMapping("finish/{userId}/{suggestId}")
    public GenericResponse finish(@PathVariable("userId") Long userId, @PathVariable("suggestId") Long suggestId) {

        return suggestBusiness.finish(userId, suggestId);
    }

    @GetMapping("findSuggest/{suggestId}")
    public GenericResponse findSuggest(@PathVariable("suggestId") Long suggestId) {

        return suggestBusiness.findSuggest(suggestId);
    }

    @GetMapping("findSuggestReply/{suggestReplyId}")
    public GenericResponse findSuggestReply(@PathVariable("suggestReplyId") Long suggestReplyId) {

        return suggestBusiness.findSuggestReply(suggestReplyId);
    }

    @PostMapping("findSuggest")
    public GenericResponse findSuggest(@RequestBody SuggestSearch suggestSearch) {

        return suggestBusiness.findSuggest(suggestSearch);
    }

    @PostMapping("findSuggestReply")
    public GenericResponse findSuggestReply(@RequestBody SuggestTopicSearch suggestTopicSearch) {
        return suggestBusiness.findSuggestReply(suggestTopicSearch);
    }

}
