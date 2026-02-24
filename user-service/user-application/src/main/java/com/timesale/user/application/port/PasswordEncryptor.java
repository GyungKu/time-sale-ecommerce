package com.timesale.user.application.port;

public interface PasswordEncryptor {

    String encrypt(String password);
    Boolean matches(String rawPassword, String encryptedPassword);

}
