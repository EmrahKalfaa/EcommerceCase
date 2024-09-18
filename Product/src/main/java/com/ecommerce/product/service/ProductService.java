package com.ecommerce.product.service;


import com.ecommerce.product.dto.ProductDTO;
import com.ecommerce.product.enums.ProductStatus;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repostory.ProductRepository;
import com.ecommerce.product.servicex.CacheRestClient;
import com.ecommerce.product.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final CacheRestClient cacheRestClient;
    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;

    public List<ProductDTO> getAllProducts() {
        List<Product> products = this.productRepository.findAll();

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> this.mapperUtil.map(product, ProductDTO.class)).toList();
        productDTOS.forEach(productDTO -> {
            this.cacheRestClient.putCache("product_" + productDTO.getId(), (Serializable) productDTO);
        });
        return productDTOS;
    }

    public ProductDTO getProductById(int productId) {
        Product product = this.productRepository.findById(Integer.valueOf(productId))
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        return this.mapperUtil.map(product, ProductDTO.class);
    }

    public List<ProductDTO> getProductWithCategory (String category){
        List<Product> products = this.productRepository.findAll()
                .stream()
                .filter(product -> category.equals(product.getCategory()))
                .toList();
        return products.stream()
                .map(product -> this.mapperUtil.map(product, ProductDTO.class)).toList();
        }

    public synchronized void increaseStock(int productId, int amount) {
        Product product = productRepository.findById(Integer.valueOf(productId))
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        product.setQuantity(product.getQuantity() + amount);
        productRepository.save(product);

        log.info("Increased stock for product id: {}, new quantity: {}", productId, product.getQuantity());
    }

    public synchronized void decreaseStock(int productId, int amount) {
        Product product = productRepository.findById(Integer.valueOf(productId))
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        if (product.getQuantity() < amount) {
            throw new IllegalStateException("Not enough stock for product id: " + productId);
        }

        product.setQuantity(product.getQuantity() - amount);
        productRepository.save(product);

        log.info("Decreased stock for product id: {}, new quantity: {}", productId, product.getQuantity());
    }


    public ProductDTO addProduct(ProductDTO productDto) {
        Product product = mapperUtil.map(productDto, Product.class);
        product.setCreatedTime(LocalDateTime.now());
        product.setUpdatedTime(LocalDateTime.now());
        product.setStatus(ProductStatus.ACTIVE);
        Product savedProduct = productRepository.save(product);
        return mapperUtil.map(savedProduct, ProductDTO.class);
    }
}
