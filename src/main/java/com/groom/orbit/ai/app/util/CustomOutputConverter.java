package com.groom.orbit.ai.app.util;

import java.util.List;

import org.springframework.ai.converter.BeanOutputConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomOutputConverter<T> {

  private final BeanOutputConverter<T> converter;
  private final ObjectMapper objectMapper;
  private final Class<T> targetType;

  public CustomOutputConverter(Class<T> converterClass) {
    this.converter = new BeanOutputConverter<>(converterClass);
    this.objectMapper = new ObjectMapper();
    this.targetType = converterClass;
  }

  public T convertToObject(String text) {
    return converter.convert(text);
  }

  public List<T> convertToList(String text) {
    try {
      // JSON 문자열을 파싱하여 리스트 형태로 변환
      text = preprocessJson(text);
      return objectMapper.readValue(text, new TypeReference<>() {});
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to convert text to List: " + text, e);
    }
  }

  public String getFormat() {
    return converter.getFormat();
  }

  private String preprocessJson(String text) {
    text = text.trim();
    if (text.startsWith("```") && text.endsWith("```")) {
      String[] lines = text.split("\n", 2);
      if (lines[0].trim().equalsIgnoreCase("```json")) {
        text = lines.length > 1 ? lines[1] : "";
      } else {
        text = text.substring(3);
      }
      text = text.substring(0, text.length() - 3);
    }
    return text.trim();
  }
}
