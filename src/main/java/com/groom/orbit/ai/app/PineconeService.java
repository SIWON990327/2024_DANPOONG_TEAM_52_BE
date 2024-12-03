package com.groom.orbit.ai.app;

import static com.groom.orbit.ai.app.util.PineconeConst.DEFAULT_MEMBER_NAME;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.openapitools.inference.client.model.Embedding;
import org.springframework.stereotype.Service;

import com.google.protobuf.Struct;
import com.google.protobuf.Struct.Builder;
import com.google.protobuf.Value;
import com.groom.orbit.ai.VectorService;
import com.groom.orbit.ai.app.dto.CreateVectorDto;
import com.groom.orbit.ai.app.dto.UpdateVectorDto;
import com.groom.orbit.ai.app.util.PineconeVectorMapper;
import com.groom.orbit.ai.dao.PineconeVectorStore;
import com.groom.orbit.ai.dao.vector.Vector;
import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;

@Service
public class PineconeService implements VectorService {

  private final PineconeVectorMapper mapper;
  private final PineconeVectorStore vectorStore;
  private final PineconeEmbeddingService embeddingService;
  private final PineconeVectorStore pineconeVectorStore;

  public PineconeService(
      PineconeVectorMapper mapper,
      PineconeVectorStore vectorStore,
      PineconeEmbeddingService embeddingService,
      PineconeVectorStore pineconeVectorStore) {
    this.mapper = mapper;
    this.vectorStore = vectorStore;
    this.embeddingService = embeddingService;
    this.pineconeVectorStore = pineconeVectorStore;
  }

  @Override
  public void save(CreateVectorDto dto) {
    Vector vector =
        findVector(dto.memberId())
            .map(existingVector -> mergeVector(existingVector, dto))
            .orElseGet(() -> createNewMemberInfoDto(dto));

    saveVector(vector, dto.memberId());
  }

  @Override
  public void update(UpdateVectorDto dto) {
    Vector vector =
        findVector(dto.memberId())
            .map(existingDto -> updateVector(existingDto, dto))
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VECTOR));

    saveVector(vector, dto.memberId());
  }

  private Optional<Vector> findVector(Long id) {
    return pineconeVectorStore.findById(id);
  }

  private void saveVector(Vector vector, Long id) {
    List<String> inputs = List.of(mapper.mapToString(vector));
    List<Embedding> embeddedInputs = embeddingService.embed(inputs);
    List<Float> embeddedVector = toEmbeddingVector(embeddedInputs);
    Struct metaData = createMetaData(vector);

    vectorStore.save(id, embeddedVector, metaData);
  }

  private Vector updateVector(Vector existingVector, UpdateVectorDto dto) {
    List<String> updatedGoals = updateList(existingVector.goals(), dto.goal());
    List<String> updatedQuests = updateList(existingVector.quests(), dto.quest());

    return Vector.builder()
        .memberId(existingVector.memberId())
        .memberName(existingVector.memberName())
        .interestJobs(existingVector.interestJobs())
        .goals(updatedGoals)
        .quests(updatedQuests)
        .build();
  }

  private List<String> updateList(List<String> existingList, String newValue) {
    if (newValue == null) {
      return existingList;
    }

    return existingList.stream().filter(item -> !item.equals(newValue)).toList().isEmpty()
        ? List.of(newValue)
        : existingList;
  }

  private Vector createNewMemberInfoDto(CreateVectorDto dto) {
    return Vector.builder()
        .memberId(dto.memberId())
        .memberName(dto.memberName() != null ? dto.memberName() : DEFAULT_MEMBER_NAME)
        .interestJobs(dto.interestJobs() != null ? dto.interestJobs() : Collections.emptyList())
        .goals(dto.goal() != null ? List.of(dto.goal()) : Collections.emptyList())
        .quests(dto.quest() != null ? List.of(dto.quest()) : Collections.emptyList())
        .build();
  }

  private Vector mergeVector(Vector existingVector, CreateVectorDto dto) {
    return Vector.builder()
        .memberId(existingVector.memberId())
        .memberName(dto.memberName() != null ? dto.memberName() : existingVector.memberName())
        .interestJobs(
            dto.interestJobs() != null && !dto.interestJobs().isEmpty()
                ? dto.interestJobs()
                : existingVector.interestJobs())
        .goals(addUniqueValue(existingVector.goals(), dto.goal()))
        .quests(addUniqueValue(existingVector.quests(), dto.quest()))
        .build();
  }

  private List<String> addUniqueValue(List<String> existingList, String newValue) {
    if (newValue != null && !existingList.contains(newValue)) {
      existingList.add(newValue);
    }
    return existingList;
  }

  private static List<Float> toEmbeddingVector(List<Embedding> embeddedInputs) {
    return embeddedInputs.stream()
        .map(Embedding::getValues)
        .filter(Objects::nonNull)
        .map(values -> values.stream().map(BigDecimal::floatValue).toList())
        .toList()
        .getFirst();
  }

  private static Struct createMetaData(Vector vector) {
    Builder builder = Struct.newBuilder();
    Arrays.stream(vector.getClass().getDeclaredFields())
        .forEach(
            field -> {
              field.setAccessible(true);
              setField(vector, field, builder);
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
