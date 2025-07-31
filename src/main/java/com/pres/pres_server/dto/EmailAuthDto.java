package com.pres.pres_server.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

//역할: 클라이언트↔서버 간 요청·응답에 쓰이는 데이터 구조체
//구성: 이메일 주소, 인증 코드 같은 필드만 가짐
//로직: 없음 (getter/setter만)

public class EmailAuthDto {

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class SendRequest {
        @NotBlank @Email
        private String email;
    }

    @Getter @AllArgsConstructor
    public static class SendResponse {
        private String message;
    }

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class VerifyRequest {
        @NotBlank @Email
        private String email;
        @NotBlank
        private String authCode;
    }

    @Getter @AllArgsConstructor
    public static class VerifyResponse {
        private boolean success;
        private String message;
    }
}