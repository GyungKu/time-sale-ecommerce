package com.timesale.user.application.port;

public interface TokenProvider {

    String generateAccessToken(Long userId, String email);
    Boolean validateToken(String token);
    Long getUserIdFromToken(String token);

}
