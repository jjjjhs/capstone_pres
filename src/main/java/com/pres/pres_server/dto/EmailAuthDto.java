package com.pres.pres_server.dto;


import lombok.*;

public class EmailAuthDto {

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class SendRequest {
        private String email;
        private String type;
    }

    @Getter @AllArgsConstructor
    public static class SendResponse {
        private String message;
    }

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class VerifyRequest {
        private String email;
        private String authNum;
    }

    @Getter @AllArgsConstructor
    public static class VerifyResponse {
        private boolean success;
        private String message;
    }
}