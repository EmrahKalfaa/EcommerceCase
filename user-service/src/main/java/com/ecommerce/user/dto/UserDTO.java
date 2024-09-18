package com.ecommerce.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserDTO implements Serializable {

    private String id;
    private String username;
    private String fullName;
    private LocalDate birthDate;
    private String mail;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
