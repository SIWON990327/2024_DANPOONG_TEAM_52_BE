package com.groom.orbit.config.oAuth;

import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
  MultiValueMap<String, String> makeBody();
}
