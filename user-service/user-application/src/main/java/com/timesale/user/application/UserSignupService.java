package com.timesale.user.application;

import com.timesale.user.application.port.PasswordEncryptor;
import com.timesale.user.domain.User;
import com.timesale.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSignupService {

    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;

    @Transactional
    public void signUp(String email, String password, String nickname) {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(email))
            throw new IllegalArgumentException("Email already in use");

        // 비밀번호 암호화
        password = passwordEncryptor.encrypt(password);

        // Entity 생성
        User user = User.builder()
            .email(email)
            .password(password)
            .nickname(nickname)
            .build();

        userRepository.save(user);
    }

}
