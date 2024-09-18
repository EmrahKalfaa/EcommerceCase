package com.ecommerce.auth.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {

    public static void setAuth(Authentication auth) {
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
