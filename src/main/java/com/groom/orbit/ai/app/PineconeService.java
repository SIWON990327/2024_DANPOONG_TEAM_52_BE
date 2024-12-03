package com.groom.orbit.ai.app;

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
import com.groom.orbit.ai.app.dto.MemberInfoDto;
import com.groom.orbit.ai.app.util.PineconeObjectMapper;
import com.groom.orbit.ai.dao.PineconeVectorStore;
import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;

@Service
public class PineconeService implements VectorService {

  private final PineconeObjectMapper mapper;
  private final PineconeVectorStore vectorStore;
  private final PineconeEmbeddingService embeddingService;
  private final PineconeVectorStore pineconeVectorStore;

  public PineconeService(
      PineconeObjectMapper mapper,
      PineconeVectorStore vectorStore,
      PineconeEmbeddingService embeddingService,
      PineconeVectorStore pineconeVectorStore) {
    this.mapper = mapper;
    this.vectorStore = vectorStore;
    this.embeddingService = embeddingService;
    this.pineconeVectorStore = pineconeVectorStore;
  }

  @Override
  public void save(MemberInfoDto dto) {
    MemberInfoDto updatedDto =
        pineconeVectorStore
            .findById(dto.memberId())
            .map(existingDto -> mergeDtos(existingDto, dto))
            .orElse(dto);

    List<String> inputs = List.of(mapper.mapToString(updatedDto));
    List<Embedding> embeddedInputs = embeddingService.embed(inputs);
    List<Float> vectors = toVector(embeddedInputs);
    Struct metaData = createMetaData(updatedDto);

    vectorStore.save(dto.memberId(), vectors, metaData);
  }

  private MemberInfoDto mergeDtos(MemberInfoDto existing, MemberInfoDto incoming) {
    return MemberInfoDto.builder()
        .memberId(existing.memberId())
        .memberName(incoming.memberName() != null ? incoming.memberName() : existing.memberName())
        .interestJobs(
            incoming.interestJobs() != null && !incoming.interestJobs().isEmpty()
                ? incoming.interestJobs()
                : existing.interestJobs())
        .goals(
            incoming.goals() != null && !incoming.goals().isEmpty()
                ? incoming.goals()
                : existing.goals())
        .quests(
            incoming.quests() != null && !incoming.quests().isEmpty()
                ? incoming.quests()
                : existing.quests())
        .build();
  }

  private static List<Float> toVector(List<Embedding> embeddedInputs) {
    return embeddedInputs.stream()
        .map(Embedding::getValues)
        .filter(Objects::nonNull)
        .map(values -> values.stream().map(BigDecimal::floatValue).toList())
        .toList()
        .getFirst();
  }

  private static Struct createMetaData(MemberInfoDto dto) {
    Builder builder = Struct.newBuilder();
    Arrays.stream(dto.getClass().getDeclaredFields())
        .forEach(
            field -> {
              field.setAccessible(true);
              setField(dto, field, builder);
            });

    return builder.build();
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
