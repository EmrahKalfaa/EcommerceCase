package com.ecommerce.product.servicex.config;

import com.ecommerce.product.servicex.CacheRestClient;
import com.ecommerce.product.util.WebClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CacheRestClientConfig {

    @Value("${ecommerce.cache-service.url:http://localhost:8083/api/v1/cache}")
    private String cacheServiceUrl;

    @Bean
    public CacheRestClient cacheRestClient(WebClient.Builder wcb) {
        return WebClientUtil.createClient(wcb, this.cacheServiceUrl, CacheRestClient.class);
    }

}
