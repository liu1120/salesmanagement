//package com.zzlbe.web.controller;
//import com.example.demo.ActionEnter;
//import org.json.JSONException;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//
///**
// * Created by ls on 2019/4/29.
// */
//@Controller
//public class UEditorController {
//
//
//    @RequestMapping("/")
//    private String showPage(){
//        return "index";
//    }
//
//    @RequestMapping(value="/config")
//    public void config(HttpServletRequest request, HttpServletResponse response) throws JSONException {
//        response.setContentType("application/json");
//        String rootPath = request.getSession().getServletContext().getRealPath("/");
//        try {
//            String exec = new ActionEnter(request, rootPath).exec();
//            PrintWriter writer = response.getWriter();
//            writer.write(exec);
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
