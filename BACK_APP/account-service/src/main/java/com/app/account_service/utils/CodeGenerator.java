package com.app.account_service.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class CodeGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generate(){
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++){
            code.append(RANDOM.nextInt(10));
        }

        return code.toString();
    }
}
