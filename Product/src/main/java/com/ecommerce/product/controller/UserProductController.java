package com.ecommerce.product.controller;

import com.ecommerce.product.constants.ProductConstants;
import com.ecommerce.product.dto.ProductDTO;
import com.ecommerce.product.service.UserProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ProductConstants.V1 + "/user")
@RequiredArgsConstructor
public class UserProductController {

    private final UserProductService userProductService;

    @PostMapping("/browsed")
    public ResponseEntity<Object> saveUserBrowsed(@PathVariable String userId,@PathVariable Integer productId) {
        this.userProductService.saveUserBrowseProduct(userId, productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bought")
    public ResponseEntity<Object> saveUserBought(@PathVariable String userId, @PathVariable Integer productId) {
        this.userProductService.saveUserBoughtProduct(userId, productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/browsed")
    public ResponseEntity<List<ProductDTO>> getUserBrowsed(@PathVariable String userId) {
        return ResponseEntity.ok(this.userProductService.getUserBrowsedProducts(userId));
    }

    @GetMapping("/bought")
    public ResponseEntity<List<ProductDTO>> getUserBought(@PathVariable String userId) {
        return ResponseEntity.ok(this.userProductService.getUserBoughtProducts(userId));
    }

}
