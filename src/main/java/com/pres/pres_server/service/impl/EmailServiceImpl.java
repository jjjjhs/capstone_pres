package com.pres.pres_server.service.impl;
import com.pres.pres_server.service.EmailService;
import lombok.RequiredArgsConstructor;

//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    //private final StringRedisTemplate redis;
    private final Map<String, String> codeStore = new ConcurrentHashMap<>();
    private final Set<String> verifiedStore = ConcurrentHashMap.newKeySet();

    //private static final String AUTH_CODE_PREFIX = "email_auth_code_";
    //private static final String VERIFIED_PREFIX  = "email_verified_";
    private static final Duration CODE_TTL = Duration.ofMinutes(5);

    @Override
    public void sendCode(String email){
        int num = (int)(Math.random()*900_000) + 100_000;
        String code = Integer.toString(num);
        //redis.opsForValue().set(PREFIX + email, code, Duration.ofMinutes(TTL));
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("회원가입 인증 코드");

        msg.setText("인증 코드: "+ code + "유효 시간: "+ CODE_TTL.toMinutes() + "분");
        mailSender.send(msg);
    }

    @Override
    public boolean verifyCode(String email, String code) {

        //String key = AUTH_CODE_PREFIX + email;
        String saved = codeStore.get(email);
        //String saved = redis.opsForValue().get(key);
        if(code.equals(saved)){
            codeStore.remove(email);
            verifiedStore.add(email);
            //redis.delete(key);
            //redis.opsForValue().set(VERIFIED_PREFIX + email, "OK", Duration.ofHours(1));
            return true;
        }
        return false;
    }

    @Override
    public boolean isVerified(String email) {
        //return "OK".equals(redis.opsForValue().get("EMAIL_VERIFIED_" + email)); //key가 없거나 key 값이
        // ok가 아닐떄 false로 반환 (=verifyCode에서 문제 발생)
    }
}
