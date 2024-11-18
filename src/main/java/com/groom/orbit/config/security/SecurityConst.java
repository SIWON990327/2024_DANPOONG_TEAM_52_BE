package com.groom.orbit.config.security;

import java.util.List;

public class SecurityConst {

  private SecurityConst() {}

  // jwt
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final long ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 30; // 30분
  public static final long REFRESH_TOKEN_EXPIRED_TIME = 1000 * 60 * 60; // 1시간

  // requestMatcher
  public static final List<String> ALLOWED_URLS = List.of("/api", "api/auth/kakao", "/kakao/**");
}
