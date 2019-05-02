package com.zzlbe.web.controller;

import com.zzlbe.core.ue.PublicMsg;
import com.zzlbe.core.ue.Ueditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UeditorController {
    @RequestMapping("/admin")
    private String showPage(){
        return "index";
    }

    @RequestMapping(value="/ueditor")
    @ResponseBody
    public String ueditor(HttpServletRequest request) {

        return PublicMsg.UEDITOR_CONFIG;
    }

    @RequestMapping(value="/imgUpload")
    @ResponseBody
    public Ueditor imgUpload(MultipartFile upfile) {
        Ueditor ueditor = new Ueditor();
        return ueditor;
    }
}