package com.timesale.user.domain.port;

import com.timesale.user.domain.User;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
