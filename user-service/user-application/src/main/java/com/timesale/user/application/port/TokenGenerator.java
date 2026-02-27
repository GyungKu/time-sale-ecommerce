package com.timesale.user.application.port;

public interface TokenGenerator {

    String generateAccessToken(Long userId, String email);

}
