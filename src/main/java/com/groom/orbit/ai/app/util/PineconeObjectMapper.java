package com.groom.orbit.ai.app.util;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import com.groom.orbit.ai.app.dto.MemberInfoDto;
import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;

@Component
public class PineconeObjectMapper {

  private final ObjectMapper mapper;

  public PineconeObjectMapper(ObjectMapper mapper) {
    this.mapper = new ObjectMapper();
  }

  public String mapToString(MemberInfoDto object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new CommonException(ErrorCode.INVALID_STATE);
    }
  }

  public MemberInfoDto fromStruct(Struct struct) {
    Map<String, Value> fields = struct.getFieldsMap();

    Long memberId = Long.parseLong(fields.get("memberId").getStringValue());
    String memberName = fields.get("memberName").getStringValue();
    List<String> interestJobs =
        convertJsonToList(fields.get("interestJobs").getStringValue(), mapper);
    List<String> goals = convertJsonToList(fields.get("goals").getStringValue(), mapper);
    List<String> quests = convertJsonToList(fields.get("quests").getStringValue(), mapper);

    return new MemberInfoDto(memberId, memberName, interestJobs, goals, quests);
  }

  private List<String> convertJsonToList(String jsonString, ObjectMapper mapper) {
    try {
      return mapper.readValue(jsonString, new TypeReference<List<String>>() {});
    } catch (JsonProcessingException e) {
      throw new CommonException(ErrorCode.INVALID_STATE);
    }
  }
}
