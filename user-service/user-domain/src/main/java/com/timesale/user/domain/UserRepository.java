package com.timesale.user.domain;

public interface UserRepository {
    User save(User user);
    Boolean existsByEmail(String email);
}
