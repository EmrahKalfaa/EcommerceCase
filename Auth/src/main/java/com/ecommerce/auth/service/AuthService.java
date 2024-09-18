package com.ecommerce.auth.service;

import com.ecommerce.auth.dto.LoginRequestDTO;
import com.ecommerce.auth.dto.LoginResponseDTO;
import com.ecommerce.auth.enums.AuthRole;
import com.ecommerce.auth.servicex.UserRestClient;
import com.ecommerce.auth.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRestClient userRestClient;

    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Boolean idValid = this.userRestClient.validateUser(loginRequestDTO.getMail(), loginRequestDTO.getPassword());
        if (!idValid) {
            throw new RuntimeException("Invalid user");
        }

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(AuthRole.USER.name()));
        try {
            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getMail(), loginRequestDTO.getPassword(), authorities)
            );

            String jwt = this.jwtService.generateJwtToken(authentication);
            SecurityContextUtil.setAuth(authentication);
            return new LoginResponseDTO(jwt);
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed", e);
        }

    }

    public LoginResponseDTO login(String deviceId) {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(AuthRole.GUEST.name()));
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(deviceId, null, authorities)
        );

        String jwt = this.jwtService.generateJwtToken(authentication);
        SecurityContextUtil.setAuth(authentication);
        return new LoginResponseDTO(jwt);
    }

}
