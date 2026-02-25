package com.timesale.user.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.timesale.common.exception.BusinessException;
import com.timesale.user.application.port.PasswordEncryptor;
import com.timesale.user.domain.User;
import com.timesale.user.domain.UserRepository;
import com.timesale.user.domain.exception.UserErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserSignupServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryptor passwordEncryptor;

    @InjectMocks
    private UserSignupService userSignupService;

    @Test
    @DisplayName("정상적인 정보가 주어지면 회원가입에 성공하고 User가 저장된다.")
    void signup_success() {
        String email = "test@test.com";
        String rawPassword = "password";
        String nickname = "test";
        String encryptedPassword = "encryptedPassword";

        given(userRepository.existsByEmail(email)).willReturn(false);
        given(passwordEncryptor.encrypt(rawPassword)).willReturn(encryptedPassword);

        userSignupService.signUp(email, rawPassword, nickname);

        verify(userRepository).existsByEmail(email);
        verify(passwordEncryptor).encrypt(rawPassword);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("이미 존재하는 이메일로 가입을 시도하면 예외가 발생한다.")
    void signup_fail_duplicated_email() {
        String email = "test@test.com";
        String rawPassword = "password";
        String nickname = "test";

        given(userRepository.existsByEmail(email)).willReturn(true);

        Assertions.assertThatThrownBy(() -> userSignupService.signUp(email, rawPassword, nickname))
            .isInstanceOf(BusinessException.class)
            .hasMessage(UserErrorCode.DUPLICATE_EMAIL.getMessage());
    }

}