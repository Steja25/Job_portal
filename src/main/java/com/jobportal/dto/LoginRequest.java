package com.jobportal.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    private String email;
    private String password;
}
