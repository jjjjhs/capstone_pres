package com.pres.pres_server.dto;

import lombok.*;

public class LoginDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginRequest {
        private String email;
        private String type;
    }

    @Getter @AllArgsConstructor
    public static class LoginResponse {
        private String message;
    }
}
