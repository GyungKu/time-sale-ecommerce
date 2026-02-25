package com.timesale.user.application;

import com.timesale.common.exception.BusinessException;
import com.timesale.user.application.port.PasswordEncryptor;
import com.timesale.user.application.port.TokenProvider;
import com.timesale.user.domain.User;
import com.timesale.user.domain.port.UserRepository;
import com.timesale.user.domain.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;
    private final TokenProvider tokenProvider;

    public String login(String email, String password) {
        User user = getByEmail(email);
        if (!passwordEncryptor.matches(password, user.getPassword()))
            throw new BusinessException(UserErrorCode.INVALID_PASSWORD);

        return tokenProvider.generateAccessToken(user.getId(), user.getEmail());
    }

    private User getByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
    }


}
