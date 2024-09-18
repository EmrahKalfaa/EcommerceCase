package com.ecommerce.recommendation.servicex.config;

import com.ecommerce.recommendation.servicex.UserRestClient;
import com.ecommerce.recommendation.util.WebClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class UserRestClientConfig {

    @Value("${ecommerce.user-service.url:http://localhost:8083/api/v1/user}")
    private String userServiceUrl;

    @Bean
    public UserRestClient userRestClient(WebClient.Builder wcb) {
        return WebClientUtil.createClient(wcb, this.userServiceUrl, UserRestClient.class);
    }


}
