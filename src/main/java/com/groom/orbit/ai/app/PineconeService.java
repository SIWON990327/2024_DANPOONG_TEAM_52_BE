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
import com.groom.orbit.ai.app.dto.MemberInfoDto;
import com.groom.orbit.ai.app.dto.UpdateVectorDto;
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
  public void save(CreateVectorDto dto) {
    MemberInfoDto createDto =
        findVector(dto.memberId())
            .map(existingDto -> mergeDtos(existingDto, dto))
            .orElseGet(() -> createNewMemberInfoDto(dto));

    saveVector(createDto, dto.memberId());
  }

  @Override
  public void update(UpdateVectorDto dto) {
    MemberInfoDto updatedDto =
        findVector(dto.memberId())
            .map(existingDto -> updateLists(existingDto, dto))
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VECTOR));

    saveVector(updatedDto, dto.memberId());
  }

  private Optional<MemberInfoDto> findVector(Long dto) {
    return pineconeVectorStore.findById(dto);
  }

  private void saveVector(MemberInfoDto createDto, Long dto) {
    List<String> inputs = List.of(mapper.mapToString(createDto));
    List<Embedding> embeddedInputs = embeddingService.embed(inputs);
    List<Float> vectors = toVector(embeddedInputs);
    Struct metaData = createMetaData(createDto);

    vectorStore.save(dto, vectors, metaData);
  }

  private MemberInfoDto updateLists(MemberInfoDto existingDto, UpdateVectorDto dto) {
    List<String> updatedGoals = updateList(existingDto.goals(), dto.goal());
    List<String> updatedQuests = updateList(existingDto.quests(), dto.quest());

    return MemberInfoDto.builder()
        .memberId(existingDto.memberId())
        .memberName(existingDto.memberName())
        .interestJobs(existingDto.interestJobs())
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

  private MemberInfoDto createNewMemberInfoDto(CreateVectorDto dto) {
    return MemberInfoDto.builder()
        .memberId(dto.memberId())
        .memberName(dto.memberName() != null ? dto.memberName() : DEFAULT_MEMBER_NAME)
        .interestJobs(dto.interestJobs() != null ? dto.interestJobs() : Collections.emptyList())
        .goals(dto.goal() != null ? List.of(dto.goal()) : Collections.emptyList())
        .quests(dto.quest() != null ? List.of(dto.quest()) : Collections.emptyList())
        .build();
  }

  private MemberInfoDto mergeDtos(MemberInfoDto existing, CreateVectorDto incoming) {
    return MemberInfoDto.builder()
        .memberId(existing.memberId())
        .memberName(incoming.memberName() != null ? incoming.memberName() : existing.memberName())
        .interestJobs(
            incoming.interestJobs() != null && !incoming.interestJobs().isEmpty()
                ? incoming.interestJobs()
                : existing.interestJobs())
        .goals(addUniqueValue(existing.goals(), incoming.goal()))
        .quests(addUniqueValue(existing.quests(), incoming.quest()))
        .build();
  }

  // 리스트에 중복되지 않는 값만 추가하는 유틸 메서드
  private List<String> addUniqueValue(List<String> existingList, String newValue) {
    if (newValue != null && !existingList.contains(newValue)) {
      existingList.add(newValue);
    }
    return existingList;
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
