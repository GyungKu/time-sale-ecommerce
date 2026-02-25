package com.timesale.user.domain;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
