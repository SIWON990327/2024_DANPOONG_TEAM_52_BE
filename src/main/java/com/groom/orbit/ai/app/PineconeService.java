package com.groom.orbit.ai.app;

import static com.groom.orbit.ai.app.util.PineconeConst.DEFAULT_MEMBER_NAME;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.groom.orbit.ai.app.dto.UpdateVectorGoalDto;
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
  public void updateGoal(UpdateVectorGoalDto dto) {
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
    String stringVector = mapper.mapToString(vector);
    List<String> inputs = List.of(stringVector);
    List<Embedding> embeddedInputs = embeddingService.embed(inputs);
    List<Float> embeddedVector = toEmbeddingVector(embeddedInputs);
    Struct metaData = createMetaData(stringVector);

    vectorStore.save(id, embeddedVector, metaData);
  }

  private Vector updateVector(Vector existingVector, UpdateVectorGoalDto dto) {
    List<String> updatedGoals = updateList(existingVector.goals(), dto.goal(), dto.newGoal());

    return Vector.builder()
        .memberId(existingVector.memberId())
        .memberName(existingVector.memberName())
        .interestJobs(existingVector.interestJobs())
        .goals(updatedGoals)
        .quests(existingVector.quests())
        .build();
  }

  private List<String> updateList(List<String> existingList, String value, String newValue) {
    if (newValue == null) {
      return existingList.stream().filter(item -> !item.equals(value)).toList();
    }

    List<String> updatedList = existingList.stream().filter(item -> !item.equals(value)).toList();
    updatedList = new ArrayList<>(updatedList);
    updatedList.add(newValue);

    return updatedList;
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

  private static Struct createMetaData(String vector) {
    Builder builder = Struct.newBuilder();
    builder.putFields("metadata", Value.newBuilder().setStringValue(vector).build());

    return builder.build();
  }
}
