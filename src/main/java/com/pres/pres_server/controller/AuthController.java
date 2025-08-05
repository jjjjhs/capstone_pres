package com.pres.pres_server.controller;

import com.pres.pres_server.dto.EmailAuthDto;
import com.pres.pres_server.dto.SignupDto;
import com.pres.pres_server.service.EmailService;
import com.pres.pres_server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController { 
    private final EmailService emailService; //final이면 안된다고 뜨는데 이유가 뭐지 받아오는거라?
    //private final UserService userService;

    @PostMapping("/signup/email")
    public ResponseEntity<EmailAuthDto.SendResponse> sendSignupEmail(@RequestBody @Valid EmailAuthDto.SendRequest req) {
        emailService.sendCode(req.getEmail());
        return ResponseEntity.ok(new EmailAuthDto.SendResponse("인증 코드 발송 완료"));
    }

    @PostMapping("/signup/email/verify") //인증코드 전송
    public ResponseEntity<EmailAuthDto.VerifyResponse> verifySignupEmail(@RequestBody @Valid EmailAuthDto.VerifyRequest req) {
        boolean success = emailService.verifyCode(req.getEmail(), req.getAuthCode());
        if (success) {
            return ResponseEntity.ok(new EmailAuthDto.VerifyResponse(true, "인증 성공"));
        }
        else {
            return ResponseEntity.badRequest().body(new EmailAuthDto.VerifyResponse(false, "인증 " +
                    "실패"));
        }
    }

    @PostMapping("/signup") //인증 후 회원가입 처리
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupDto dto) {
        emailService.isVerified(dto.getEmail());
        //userService.signup(dto);
        return ResponseEntity.ok().build();
    }

}
