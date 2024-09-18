package com.ecommerce.recommendation.service;

import com.ecommerce.recommendation.servicex.ProductRestClient;
import com.ecommerce.recommendation.servicex.UserRestClient;
import com.ecommerce.recommendation.servicex.model.Product;
import com.ecommerce.recommendation.servicex.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final UserRestClient userRestClient;

    private final ProductRestClient productRestClient;

    public List<Product> getRecommendedProducts(String userId) {
        User user = this.userRestClient.getUserById(userId);
        List<Product> userProducts = this.productRestClient.getUserBought(userId);
        List<User> similarUsers = this.getSimilarUsers(user);
        List<Product> recommendedProducts = new ArrayList<>();
        for (User similarUser : similarUsers) {
            List<Product> products = this.productRestClient.getUserBought(similarUser.getId());
            for (Product product : products) {
                if (!userProducts.contains(product)) {
                    recommendedProducts.add(product);
                }
            }
        }
        return recommendedProducts;
    }

    private List<User> getSimilarUsers(User user) {
        List<User> similarUsers = new ArrayList<>();
        for (User otherUser : this.userRestClient.getAllUsers()) {
            if (otherUser != user) {
                int similarityScore = this.calculateSimilarityScore(user, otherUser);
                if (similarityScore > 0) {
                    similarUsers.add(otherUser);
                }
            }
        }
        return similarUsers;
    }

    private int calculateSimilarityScore(User user1, User user2) {
        List<Product> user1Products = this.productRestClient.getUserBrowsed(user1.getId());
        List<Product> user2Products = this.productRestClient.getUserBrowsed(user2.getId());
        Set<Product> intersection = new HashSet<>(user1Products);
        intersection.retainAll(user2Products);
        return intersection.size();
    }
}
