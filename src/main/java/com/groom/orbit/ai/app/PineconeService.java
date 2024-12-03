package com.groom.orbit.ai.app;

import static com.groom.orbit.ai.app.util.PineconeConst.INTEREST_JOB_NAMESPACE;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.openapitools.inference.client.model.Embedding;
import org.springframework.stereotype.Service;

import com.google.protobuf.Struct;
import com.google.protobuf.Struct.Builder;
import com.google.protobuf.Value;
import com.groom.orbit.ai.VectorService;
import com.groom.orbit.ai.app.dto.MemberInterestJobDto;
import com.groom.orbit.ai.app.util.PineconeObjectMapper;
import com.groom.orbit.ai.dao.PineconeVectorStore;
import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;

@Service
public class PineconeService implements VectorService {

  private final PineconeObjectMapper mapper;
  private final PineconeVectorStore vectorStore;
  private final PineconeEmbeddingService embeddingService;

  public PineconeService(
      PineconeObjectMapper mapper,
      PineconeVectorStore vectorStore,
      PineconeEmbeddingService embeddingService) {
    this.mapper = mapper;
    this.vectorStore = vectorStore;
    this.embeddingService = embeddingService;
  }

  @Override
  public void saveMember(MemberInterestJobDto dto) {
    List<String> inputs = List.of(mapper.mapToString(dto));
    List<Embedding> embeddedInputs = embeddingService.embed(inputs);
    List<Float> vectors = toVector(embeddedInputs);
    Struct metaData = createMetaData(dto);

    vectorStore.save(vectors, metaData, INTEREST_JOB_NAMESPACE);
  }

  private static Struct createMetaData(Object dto) {
    Builder builder = Struct.newBuilder();
    if (!dto.getClass().isRecord()) {
      throw new CommonException(ErrorCode.INVALID_STATE);
    }

    Arrays.stream(dto.getClass().getDeclaredFields())
        .forEach(
            field -> {
              field.setAccessible(true);
              setField(dto, field, builder);
            });

    return builder.build();
  }

  private static List<Float> toVector(List<Embedding> embeddedInputs) {
    return embeddedInputs.stream()
        .map(Embedding::getValues)
        .filter(Objects::nonNull)
        .map(values -> values.stream().map(BigDecimal::floatValue).toList())
        .toList()
        .getFirst();
  }

  private static void setField(Object dto, Field field, Builder builder) {
    try {
      Object value = field.get(dto);
      if (value != null) {
        builder.putFields(
            field.getName(), Value.newBuilder().setStringValue(value.toString()).build());
      }
    } catch (IllegalAccessException e) {
      throw new CommonException(ErrorCode.INVALID_STATE);
    }
  }
}
