package com.pres.pres_server.service.impl;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.pres.pres_server.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redis;
    private static final String PREFIX = "email_";
    private static final long TTL = 60 * 60 * 24 * 7;

    @Override
    public void sendCode(String email){
        int num = (int)(Math.random()*900_000) + 100_000;
        String code = Integer.toString(num);
        redis.opsForValue().set(PREFIX + email, code, Duration.ofMinutes(TTL));
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("회원가입 인증 코드");
        msg.setText("인증 코드: "+ code + "유효 시간: "+ TTL + "분");
        mailSender.send(msg);
    }

    @Override
    public boolean verifyCode(String email, String code) {
        String key = PREFIX + email;
        String saved = redis.opsForValue().get(key);
        if(saved != null && saved.equals(code)){
            redis.delete(key);
            redis.opsForValue().set("email verified" + email, "OK", Duration.ofHours(1));
            return true;
        }
        return false;
    }

    @Override
    public boolean isVerified(String email) {
        return "OK".equals(redis.opsForValue().get("EMAIL_VERIFIED_" + email)); //key가 없거나 key 값이
        // ok가 아닐떄 false로 반환 (=verifyCode에서 문제 발생)
    }
}
