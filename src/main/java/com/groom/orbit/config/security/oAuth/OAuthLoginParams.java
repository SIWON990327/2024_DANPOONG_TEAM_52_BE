package com.groom.orbit.config.security.oAuth;

import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
  MultiValueMap<String, String> makeBody();
}
