package com.timesale.common.security;

import com.timesale.common.exception.BusinessException;
import com.timesale.common.exception.auth.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenValidator tokenValidator;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class) &&
            Long.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public @Nullable Object resolveArgument(MethodParameter parameter,
        @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
        @Nullable WebDataBinderFactory binderFactory) throws Exception {

        String bearerToken = webRequest.getHeader(AUTHORIZATION_HEADER);

        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(BEARER_PREFIX))
            throw new BusinessException(AuthErrorCode.TOKEN_EMPTY);

        String token = bearerToken.substring(BEARER_PREFIX.length());

        if (!tokenValidator.validateToken(token))
            throw new BusinessException(AuthErrorCode.INVALID_TOKEN);

        return tokenValidator.getUserIdFromToken(token);
    }
}
