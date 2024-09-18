package com.ecommerce.product.repostory;

import com.ecommerce.product.model.UserBoughtProduct;
import com.ecommerce.product.model.UserBrowseProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBoughtProductRepository extends MongoRepository<UserBoughtProduct, String> {

    List<UserBoughtProduct> findByUserId(String userId);

}
