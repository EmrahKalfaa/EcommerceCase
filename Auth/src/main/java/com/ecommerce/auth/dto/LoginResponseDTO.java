package com.ecommerce.auth.dto;

import java.io.Serializable;

public record LoginResponseDTO(String token) implements Serializable {

}