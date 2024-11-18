package com.groom.orbit.config.security.oAuth;

import static com.groom.orbit.config.security.SecurityConst.ACCESS_TOKEN_EXPIRED_TIME;
import static com.groom.orbit.config.security.SecurityConst.REFRESH_TOKEN_EXPIRED_TIME;
import static com.groom.orbit.config.security.SecurityConst.TOKEN_PREFIX;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.groom.orbit.config.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthTokenGenerator {

  private final JwtTokenProvider jwtTokenProvider;

  public AuthToken generate(Long memberId) {
    long now = (new Date()).getTime();

    Date accessTokenExpiredTime = new Date(now + ACCESS_TOKEN_EXPIRED_TIME);
    Date refreshTokenExpiredTime = new Date(now + REFRESH_TOKEN_EXPIRED_TIME);

    String subject = memberId.toString();
    String accessToken = jwtTokenProvider.generate(subject, accessTokenExpiredTime);
    String refreshToken = jwtTokenProvider.generate(subject, refreshTokenExpiredTime);

    return AuthToken.of(accessToken, refreshToken, TOKEN_PREFIX, ACCESS_TOKEN_EXPIRED_TIME / 1000L);
  }

  public Long extractMemberId(String accessToken) {
    return Long.valueOf(jwtTokenProvider.extractSubject(accessToken));
  }
}
