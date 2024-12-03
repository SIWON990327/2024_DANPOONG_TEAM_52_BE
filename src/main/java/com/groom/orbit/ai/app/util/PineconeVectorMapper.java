package com.groom.orbit.ai.app.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Struct;
import com.groom.orbit.ai.dao.vector.Vector;
import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;

@Component
public class PineconeVectorMapper {

  private final ObjectMapper mapper;

  public PineconeVectorMapper(ObjectMapper mapper) {
    this.mapper = new ObjectMapper();
  }

  public String mapToString(Vector vector) {
    try {
      return mapper.writeValueAsString(vector);
    } catch (JsonProcessingException e) {
      throw new CommonException(ErrorCode.INVALID_STATE);
    }
  }

  public Vector fromStruct(Struct struct) {
    try {
      String jsonString = struct.getFieldsMap().get("metadata").getStringValue();
      return new ObjectMapper().readValue(jsonString, Vector.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
