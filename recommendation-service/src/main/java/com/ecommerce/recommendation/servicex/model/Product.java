package com.ecommerce.recommendation.servicex.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private String image;
    private String category;
    private String brand;
    private String model;
}
