package com.groom.orbit.ai.dao;

import static com.groom.orbit.ai.app.util.PineconeConst.INDEX_NAME;
import static com.groom.orbit.ai.app.util.PineconeConst.INTEREST_JOB_NAMESPACE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.protobuf.Struct;
import com.groom.orbit.ai.app.dto.MemberInfoDto;
import com.groom.orbit.ai.app.util.PineconeObjectMapper;

import io.pinecone.clients.Index;
import io.pinecone.clients.Pinecone;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;
import io.pinecone.unsigned_indices_model.ScoredVectorWithUnsignedIndices;

@Component
public class PineconeVectorStore {

  private final Index index;
  private final PineconeObjectMapper mapper;

  @Value("${spring.ai.vectorstore.pinecone.api-key}")
  private String PINECONE_API_KEY;

  public PineconeVectorStore(Pinecone pinecone, PineconeObjectMapper mapper) {
    this.index = pinecone.getIndexConnection(INDEX_NAME);
    this.mapper = mapper;
  }

  public void save(Long key, List<Float> vectors, Struct metadata) {
    upsert(key, vectors, metadata);
  }

  public Optional<MemberInfoDto> findById(Long key) {
    String findKey = getKey(key);
    QueryResponseWithUnsignedIndices response = index.queryByVectorId(1, findKey);
    ScoredVectorWithUnsignedIndices matches = response.getMatches(1);
    if (matches.getId().equals(findKey)) {
      return Optional.of(mapper.fromStruct(matches.getMetadata()));
    }
    return Optional.empty();
  }

  private void upsert(Long key, List<Float> vector, Struct metadata) {
    index.upsert(getKey(key), vector, null, null, metadata, INTEREST_JOB_NAMESPACE);
  }

  private String getKey(Long key) {
    return key.toString();
  }
}
