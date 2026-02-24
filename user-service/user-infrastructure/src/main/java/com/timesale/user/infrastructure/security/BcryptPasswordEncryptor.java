package com.timesale.user.infrastructure.security;

import com.timesale.user.application.port.PasswordEncryptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncryptor implements PasswordEncryptor {

    private final PasswordEncoder passwordEncoder;

    public BcryptPasswordEncryptor() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public Boolean matches(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }
}
