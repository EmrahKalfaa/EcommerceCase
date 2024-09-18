package com.ecommerce.product.repostory;

import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.UserBrowseProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBrowseProductRepository extends MongoRepository<UserBrowseProduct, String> {

    List<UserBrowseProduct> findByUserId(String userId);
}
