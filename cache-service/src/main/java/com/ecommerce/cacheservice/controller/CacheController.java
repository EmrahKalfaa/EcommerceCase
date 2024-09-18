package com.ecommerce.cacheservice.controller;

import com.ecommerce.cacheservice.constant.CacheConstant;
import com.ecommerce.cacheservice.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping(CacheConstant.API_V1)
@ConditionalOnProperty(name = "ecommerce.cache.enabled", havingValue = "true")
@RequiredArgsConstructor
public class CacheController {

    private final CacheService cacheService;

    @GetMapping
    public ResponseEntity<Serializable> getAllCache() {
        return ResponseEntity.ok(this.cacheService.getAllCache());
    }

    @GetMapping("/{key}")
    public ResponseEntity<Serializable> getCache(@PathVariable String key) {
        return ResponseEntity.ok(this.cacheService.getCache(key));
    }

    @PostMapping
    public ResponseEntity<Serializable> putCache(@RequestParam String key, @RequestBody Serializable value) {
        this.cacheService.putCache(key, value);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Serializable> clearAllCache() {
        this.cacheService.clearAllCache();
        return ResponseEntity.ok().build();
    }
}
