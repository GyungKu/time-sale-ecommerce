package com.timesale.user.application.port;

public interface TokenProvider {

    String generateAccessToken(Long userId, String email);

}
