package com.ecommerce.auth.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequestDTO implements Serializable {

    public String mail;
    public String password;

}

