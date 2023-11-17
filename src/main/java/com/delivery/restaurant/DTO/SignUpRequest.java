package com.delivery.restaurant.DTO;

import lombok.Data;

@Data
public class SignUpRequest {

    private String name;
    private String password;
    private String email;
    private String phone;

    private String role;
}

