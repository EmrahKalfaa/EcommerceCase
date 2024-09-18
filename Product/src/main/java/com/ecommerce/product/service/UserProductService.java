package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductDTO;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.UserBoughtProduct;
import com.ecommerce.product.model.UserBrowseProduct;
import com.ecommerce.product.repostory.UserBoughtProductRepository;
import com.ecommerce.product.repostory.UserBrowseProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProductService {

    private final ProductService productService;

    private final UserBrowseProductRepository userBrowseProductRepository;

    private final UserBoughtProductRepository userBoughtProductRepository;

    public void saveUserBrowseProduct(String userId, Integer productId) {
        this.userBrowseProductRepository.save(UserBrowseProduct.builder().userId(userId).productId(productId).build());
    }

    public void saveUserBoughtProduct(String userId, Integer productId) {
        this.userBoughtProductRepository.save(UserBoughtProduct.builder().userId(userId).productId(productId).build());
    }

    public List<ProductDTO> getUserBrowsedProducts(String userId) {
        List<UserBrowseProduct> userBoughtProduct = this.userBrowseProductRepository.findByUserId(userId);
        return userBoughtProduct.stream().map(UserBrowseProduct::getProductId)
                .map(this.productService::getProductById)
                .toList();
    }

    public List<ProductDTO> getUserBoughtProducts(String userId) {
        List<UserBoughtProduct> userBoughtProduct = this.userBoughtProductRepository.findByUserId(userId);
        return userBoughtProduct.stream().map(UserBoughtProduct::getProductId)
                .map(this.productService::getProductById)
                .toList();
    }
}
