//package com.zzlbe.core.config;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.text.SimpleDateFormat;
//import java.util.List;
//
//@Configuration
//public class Configurer implements WebMvcConfigurer {
//
//    @Autowired
//    HttpInterceptor httpInterceptor;
//
//    //定义时间格式转换器
//    @Bean
//    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
//        converter.setObjectMapper(mapper);
//        return converter;
//    }
//
//    //添加转换器
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        //将我们定义的时间格式转换器添加到转换器列表中,
//        //这样jackson格式化时候但凡遇到Date类型就会转换成我们定义的格式
//        converters.add(jackson2HttpMessageConverter());
//    }
//
//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//        // TODO Auto-generated method stub
//        registry.addInterceptor(httpInterceptor).addPathPatterns("/**");
//        super.addInterceptors(registry);
//    }
//}
