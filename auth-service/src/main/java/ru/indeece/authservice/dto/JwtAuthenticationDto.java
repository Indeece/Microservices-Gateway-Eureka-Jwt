package ru.indeece.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationDto {
    private Long userId;
    private String token;
    private String refreshToken;
}
