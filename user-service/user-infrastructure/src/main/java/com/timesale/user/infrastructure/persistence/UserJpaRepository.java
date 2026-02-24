package com.timesale.user.infrastructure.persistence;

import com.timesale.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
}
