package org.example.authbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private String role;
}
