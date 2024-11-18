package com.groom.orbit.auth.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.auth.dao.MemberRepository;
import com.groom.orbit.auth.dao.entity.Member;
import com.groom.orbit.config.security.oAuth.AuthToken;
import com.groom.orbit.config.security.oAuth.AuthTokenGenerator;
import com.groom.orbit.config.security.oAuth.OAuthInfoResponse;
import com.groom.orbit.config.security.oAuth.OAuthLoginParams;
import com.groom.orbit.config.security.oAuth.RequestOAuthInfoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

  private final MemberRepository memberRepository;
  private final AuthTokenGenerator authTokensGenerator;
  private final RequestOAuthInfoService requestOAuthInfoService;

  public AuthToken login(OAuthLoginParams params) {
    OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
    Long userId = findOrCreateMember(oAuthInfoResponse);
    return authTokensGenerator.generate(userId);
  }

  public Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
    return memberRepository
        .findByNickname(oAuthInfoResponse.getKakaoNickname())
        .map(Member::getId)
        .orElseGet(() -> newUser(oAuthInfoResponse));
  }

  private Long newUser(OAuthInfoResponse oAuthInfoResponse) {
    Member member = Member.builder().nickname(oAuthInfoResponse.getKakaoNickname()).build();

    return memberRepository.save(member).getId();
  }
}
