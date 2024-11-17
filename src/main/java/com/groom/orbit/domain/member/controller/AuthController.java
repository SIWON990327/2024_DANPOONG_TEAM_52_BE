package com.groom.orbit.domain.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.exception.BaseResponse;
import com.groom.orbit.config.kakao.KakaoLoginParams;
import com.groom.orbit.config.oAuth.AuthToken;
import com.groom.orbit.domain.member.app.AuthService;

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
