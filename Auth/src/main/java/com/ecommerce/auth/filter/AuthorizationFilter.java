package com.ecommerce.auth.filter;

import com.ecommerce.auth.constant.AuthConstant;
import com.ecommerce.auth.enums.AuthRole;
import com.ecommerce.auth.service.AuthService;
import com.ecommerce.auth.service.JwtService;
import com.ecommerce.auth.util.SecurityContextUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String header = req.getHeader(HttpHeaders.AUTHORIZATION);
        if(isSupportJwt(header)) {
            this.authenticate(header);
        }

        filterChain.doFilter(req, res);
    }

    public static boolean isSupportJwt(String header) {
        return header != null && header.startsWith(AuthConstant.BEARER);
    }

    private void authenticate(String header) {
        String jwt = header.substring(AuthConstant.BEARER.length()).trim();
        if (this.jwtService.validateJwtToken(jwt)) {
            String mail = this.jwtService.getEmailFromJwtToken(jwt);
            String role = this.jwtService.getAuthFromJwtToken(jwt);

            UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken.authenticated(mail, null, List.of(new SimpleGrantedAuthority(role)));
            SecurityContextUtil.setAuth(authentication);
        }
    }

}
