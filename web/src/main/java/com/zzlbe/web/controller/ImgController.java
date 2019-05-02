package com.zzlbe.web.controller;

import com.alibaba.fastjson.JSON;
import com.zzlbe.core.util.FileUtils;
import com.zzlbe.dao.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class ImgController {


    private final ResourceLoader resourceLoader;

    @Value("${web.upload-path}")
    private String path;

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    public ImgController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     *
     * @param file 要上传的文件
     * @return
     */
    @PostMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("fileName") MultipartFile file, Map<String, Object> map){
        System.out.println("file1:"+file);
        // 要上传的目标文件存放路径
        String localPath = "E:/Files/Photos";
        // 上传成功或者失败的提示
        String msg = "";
        if (FileUtils.upload(file, localPath, file.getOriginalFilename())){
            // 上传成功，给出页面提示
            msg = "上传成功！";
        }else {
            msg = "上传失败！";
        }
        // 显示图片
        map.put("msg", msg);
        map.put("fileName", file.getOriginalFilename());
        map.put("filePath", "/Photos/"+file.getOriginalFilename());
        String str=JSON.toJSONString(map);
        return str;
    }

    @PostMapping("fileUpload2")
    @ResponseBody
    public String fileUpload2(@RequestParam("fileName") MultipartFile file, Map<String, Object> map){
        // 要上传的目标文件存放路径
        String localPath = "E:/Files/Photos";
        System.out.println("file2:"+file);
        // 上传成功或者失败的提示
        String msg = "";
        if (FileUtils.upload(file, localPath, file.getOriginalFilename())){
            // 上传成功，给出页面提示
            msg = "上传成功！";
        }else {
            msg = "上传失败！";
        }
        return file.getOriginalFilename();
    }

    /**
     * 显示单张图片
     * @return
     */
    @RequestMapping("show")
    public ResponseEntity showPhotos(String fileName){
        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + path + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * ue 图片上传
     */
    @RequestMapping(value = "/fileUpload3")
    @ResponseBody
    public String fileUpload3(MultipartFile upfile) {
        String path = this.fileUpload2(upfile,null);
        System.out.println("path333:"+path);
//        path="/Photos/"+path;
        String config =
                "{\n" +
                        "            \"state\": \"SUCCESS\",\n" +
                        "                \"url\": \"/Photos/"+path+"\",\n" +
                        "                \"title\": \"+path+\",\n" +
                        "                \"original\": \"+path+\"\n" +
                        "        }";
        return config;
    }
}
