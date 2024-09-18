package com.ecommerce.recommendation.servicex;

import com.ecommerce.recommendation.servicex.model.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface ProductRestClient {

    @GetExchange("/browsed")
    List<Product> getUserBrowsed(@PathVariable String userId);

    @GetExchange("/bought")
    List<Product> getUserBought(@PathVariable String userId);
}
