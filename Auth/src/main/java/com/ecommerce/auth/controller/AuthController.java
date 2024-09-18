package com.ecommerce.auth.controller;

import com.ecommerce.auth.constant.AuthConstant;
import com.ecommerce.auth.controller.doc.AuthControllerDoc;
import com.ecommerce.auth.dto.LoginRequestDTO;
import com.ecommerce.auth.dto.LoginResponseDTO;
import com.ecommerce.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AuthConstant.API_V1)
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {

    private final AuthService authService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(this.authService.login(loginRequestDTO));
    }

    @PostMapping("/login/light")
    public ResponseEntity<LoginResponseDTO> login(@RequestParam("deviceId") String deviceId) {
        return ResponseEntity.ok(this.authService.login(deviceId));
    }

}
