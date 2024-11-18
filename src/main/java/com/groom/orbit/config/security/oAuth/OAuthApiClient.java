package com.groom.orbit.config.security.oAuth;

public interface OAuthApiClient {

  String requestAccessToken(OAuthLoginParams params);

  OAuthInfoResponse requestOauthInfo(String accessToken);
}
