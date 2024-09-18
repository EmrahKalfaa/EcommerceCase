package com.ecommerce.product.controller;

import com.ecommerce.product.constants.ProductConstants;
import com.ecommerce.product.dto.ProductDTO;
import com.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ProductConstants.V1)
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(this.productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDto) {
        ProductDTO savedProduct = this.productService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
    
    @GetMapping("/caregory")
    public ResponseEntity<List<ProductDTO>> getProductWithCategory(@RequestParam String categoryName) {
        return ResponseEntity.ok(this.productService.getProductWithCategory(categoryName));
    }

    @PutMapping("/increaseStock")
    public ResponseEntity<Object> increaseStock(@RequestParam int productId, @RequestParam int amount) {
        this.productService.increaseStock(productId,amount);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/decreaceStock")
    public ResponseEntity<Object> decreaseStock(@RequestParam int productId, @RequestParam int amount) {
        this.productService.decreaseStock(productId,amount);
        return ResponseEntity.ok().build();
    }

}
