package com.example.project3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

    private String username;
    private String password;
    private String name;
    private String email;
    private String role;
    private String phone;
}
