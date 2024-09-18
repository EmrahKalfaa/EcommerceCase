package com.ecommerce.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "user_browse_product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserBrowseProduct {

    @Id
    private String id;
    private String userId;
    private Integer productId;
}
