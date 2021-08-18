package com.kwon770.mm.config;

import com.kwon770.mm.exception.CustomAuthenticationException;
import com.kwon770.mm.provider.security.JwtAuthToken;
import com.kwon770.mm.provider.security.JwtAuthTokenProvider;
import com.kwon770.mm.provider.security.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private static final String AUTHORIZATION_HEADER = "x-auth-token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle!!");

        Optional<String> token = resolveToken(request);

        if (token.isPresent()) {
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            if (jwtAuthToken.validate() & Role.USER.getCode().equals(jwtAuthToken.getData().get("role"))) {
                return true;
            }

            throw new CustomAuthenticationException();
        }

        throw new CustomAuthenticationException();
    }

    private Optional<String> resolveToken(HttpServletRequest request) {
        String authToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(authToken)) {
            return Optional.of(authToken);
        }

        return Optional.empty();
    }
}
