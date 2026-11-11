package com.app.account_service.services;

import com.app.account_service.exceptions.CodeAlreadySentException;
import com.app.account_service.exceptions.TooManyAttemptsException;
import com.app.account_service.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {
    private final CodeGenerator codeGenerator;
    private final StringRedisTemplate redisTemplate;

    private static final Duration CODE_TTL = Duration.ofMinutes(5);
    private static final int MAX_ATTEMPTS = 5;

    public String generate(String key) {
        String code = codeGenerator.generate();
        Boolean created = redisTemplate.opsForValue().setIfAbsent(key, code, CODE_TTL);
        if (!Boolean.TRUE.equals(created)) throw new CodeAlreadySentException();
        redisTemplate.delete(attemptsKey(key));
        return code;
    }

    public String regenerate(String key) {
        redisTemplate.delete(key);
        return generate(key);
    }

    public boolean check(String key, String code) {
        String value = redisTemplate.opsForValue().get(key);
        if (value == null) return false;

        if (value.equals(code)) {
            redisTemplate.delete(List.of(key, attemptsKey(key)));
            return true;
        }

        if (registerFailedAttempt(key) >= MAX_ATTEMPTS) {
            redisTemplate.delete(List.of(key, attemptsKey(key)));
            throw new TooManyAttemptsException();
        }
        return false;
    }

    private long registerFailedAttempt(String key) {
        Long attempts = redisTemplate.opsForValue().increment(attemptsKey(key));
        if (attempts == null) return 0;
        if (attempts == 1) redisTemplate.expire(attemptsKey(key), CODE_TTL);
        return attempts;
    }

    private String attemptsKey(String key) {
        return key + ":attempts";
    }

    public static long Duration(){
        return CODE_TTL.toMinutes();
    }
}
