package com.ecommerce.recommendation.servicex.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private String id;
    private String username;
    private String fullName;
    private LocalDate birthDate;
    private String mail;
    private String password;
}
