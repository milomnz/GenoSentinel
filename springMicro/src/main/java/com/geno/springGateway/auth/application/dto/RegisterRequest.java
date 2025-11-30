package com.geno.springGateway.auth.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private List<String> roles;
}
