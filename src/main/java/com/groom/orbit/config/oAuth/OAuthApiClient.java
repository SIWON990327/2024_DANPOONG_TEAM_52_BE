package com.groom.orbit.config.oAuth;

public interface OAuthApiClient {

  String requestAccessToken(OAuthLoginParams params);

  OAuthInfoResponse requestOauthInfo(String accessToken);
}
