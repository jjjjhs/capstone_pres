package com.pres.pres_server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupDto {
    private int id;
    private String username;

    @Email @NotBlank
    private String email;
    @NotBlank
    private String password;

    public static class SignupRequest {
        @Email @NotBlank
        private String email;
        @NotBlank
        private String password;
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
