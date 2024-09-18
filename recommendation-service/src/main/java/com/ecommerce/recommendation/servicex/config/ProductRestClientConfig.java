package com.ecommerce.recommendation.servicex.config;

import com.ecommerce.recommendation.servicex.ProductRestClient;
import com.ecommerce.recommendation.util.WebClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ProductRestClientConfig {

    @Value("${ecommerce.product-service.url:http://localhost:8083/api/v1/product/user}")
    private String productServiceUrl;

    @Bean
    public ProductRestClient productRestClient(WebClient.Builder wcb) {
        return WebClientUtil.createClient(wcb, this.productServiceUrl, ProductRestClient.class);
    }
}
