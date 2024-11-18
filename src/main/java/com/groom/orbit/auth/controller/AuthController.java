package com.groom.orbit.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.auth.app.AuthService;
import com.groom.orbit.common.exception.BaseResponse;
import com.groom.orbit.config.security.kakao.KakaoLoginParams;
import com.groom.orbit.config.security.oAuth.AuthToken;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/kakao")
  public BaseResponse<AuthToken> loginKakao(@RequestBody KakaoLoginParams params) {
    return BaseResponse.onSuccess(authService.login(params));
  }
}
