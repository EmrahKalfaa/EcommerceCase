package com.ecommerce.auth.servicex;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserRestClient {

    @GetMapping("/validate")
    Boolean validateUser(@RequestParam("mail") String mail, @RequestParam("password") String password);
}
