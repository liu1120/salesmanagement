package com.zzlbe.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * PROJECT: salesmanagement
 * DESCRIPTION: TODO
 *
 * @author amos.wang
 * @date 2019/4/23
 */
@Configuration
public class SalesConfig {

    @Bean
    RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(60000);

        return new RestTemplate(requestFactory);
    }

}
