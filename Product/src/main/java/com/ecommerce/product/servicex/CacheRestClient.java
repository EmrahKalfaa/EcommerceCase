package com.ecommerce.product.servicex;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.PostExchange;

import java.io.Serializable;

public interface CacheRestClient {

    @PostExchange
    void putCache(@RequestParam String key, @RequestBody Serializable value);
}
