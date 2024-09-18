package com.ecommerce.recommendation.servicex;

import com.ecommerce.recommendation.servicex.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface UserRestClient {

    @GetExchange("/{userId}")
    User getUserById(@PathVariable String userId);

    @GetExchange
    List<User> getAllUsers();
}
