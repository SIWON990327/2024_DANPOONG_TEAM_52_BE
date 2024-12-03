package com.groom.orbit.ai.app.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

@Component
public class PineconeObjectMapper {

  private final ObjectMapper objectMapper;

  public PineconeObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = new ObjectMapper();
  }

  @SneakyThrows
  public String mapToString(Object object) {
    return objectMapper.writeValueAsString(object);
  }
}
