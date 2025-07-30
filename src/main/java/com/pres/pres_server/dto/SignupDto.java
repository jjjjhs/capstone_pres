package com.pres.pres_server.dto;

import lombok.*;

public class SignupDto {
    private int id;
    private String username;
    private String password;
    private String email;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignupRequest {
        private String email;
        private String type;
    }

    @Getter @AllArgsConstructor
    public static class SignupResponse {
        private String message;
    }

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class VerifyRequest {
        private String email;
        private String authNum;
    }

    @Getter @AllArgsConstructor @NoArgsConstructor @Builder
    public static class VerifyResponse {
        private boolean success;
        private String message;
    }
}
