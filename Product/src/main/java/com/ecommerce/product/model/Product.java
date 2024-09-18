package com.ecommerce.product.model;

import com.ecommerce.product.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(value = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {

    @Id
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private String image;
    private String category;
    private String brand;
    private String model;
    private LocalDateTime updatedTime;
    private LocalDateTime createdTime;
    private ProductStatus status;

}
