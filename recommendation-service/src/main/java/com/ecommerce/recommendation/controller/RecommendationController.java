package com.ecommerce.recommendation.controller;

import com.ecommerce.recommendation.constant.RecommendationConstant;
import com.ecommerce.recommendation.service.RecommendationService;
import com.ecommerce.recommendation.servicex.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RecommendationConstant.API_V1)
@RequiredArgsConstructor
public class RecommendationController {

    private RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public List<Product> getRecommendations(@PathVariable String userId) {
        List<Product> recommendedProducts = this.recommendationService.getRecommendedProducts(userId);
        return recommendedProducts;
    }
}
