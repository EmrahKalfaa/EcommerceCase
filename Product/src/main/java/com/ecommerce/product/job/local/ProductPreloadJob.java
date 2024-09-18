package com.ecommerce.product.job.local;

import com.ecommerce.product.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPreloadJob {

    private final ProductService productService;

    @PostConstruct
    public void init() {
        this.preloadProducts();
    }

    @Scheduled(cron = "*/5 * * * *")
    public void preloadProducts() {
        this.productService.getAllProducts();
    }
}
