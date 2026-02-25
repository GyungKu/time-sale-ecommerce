package com.timesale.user.api.controller;

import com.timesale.user.api.dto.request.LoginRequest;
import com.timesale.user.api.dto.request.SignupRequest;
import com.timesale.user.api.dto.response.LoginResponse;
import com.timesale.user.api.security.LoginUser;
import com.timesale.user.application.UserLoginService;
import com.timesale.user.application.UserSignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserSignupService userSignupService;
    private final UserLoginService userLoginService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        userSignupService.signUp(request.email(), request.password(), request.nickname());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = userLoginService.login(request.email(), request.password());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<String> getMyInfo(@LoginUser Long userId) {
        return ResponseEntity.ok("userId: " + userId);
    }

}
