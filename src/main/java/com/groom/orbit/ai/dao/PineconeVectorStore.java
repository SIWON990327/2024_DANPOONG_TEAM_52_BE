package com.groom.orbit.ai.dao;

import static com.groom.orbit.ai.app.util.PineconeConst.INDEX_NAME;
import static com.groom.orbit.ai.app.util.PineconeConst.INTEREST_JOB_NAMESPACE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.protobuf.Struct;
import com.groom.orbit.ai.app.util.PineconeVectorMapper;
import com.groom.orbit.ai.dao.vector.Vector;

import io.pinecone.clients.Index;
import io.pinecone.clients.Pinecone;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;

@Component
public class PineconeVectorStore {

  private final Index index;
  private final PineconeVectorMapper mapper;

  @Value("${spring.ai.vectorstore.pinecone.api-key}")
  private String PINECONE_API_KEY;

  public PineconeVectorStore(Pinecone pinecone, PineconeVectorMapper mapper) {
    this.index = pinecone.getIndexConnection(INDEX_NAME);
    this.mapper = mapper;
  }

  public void save(Long key, List<Float> vectors, Struct metadata) {
    upsert(key, vectors, metadata);
  }

  public Optional<Vector> findById(Long key) {
    String findKey = getKey(key);
    QueryResponseWithUnsignedIndices response = index.queryByVectorId(1, findKey);

    return response.getMatchesList().stream()
        .filter(match -> findKey.equals(match.getId()))
        .findFirst()
        .map(match -> mapper.fromStruct(match.getMetadata()));
  }

  private void upsert(Long key, List<Float> vector, Struct metadata) {
    index.upsert(getKey(key), vector, null, null, metadata, INTEREST_JOB_NAMESPACE);
  }

  private String getKey(Long key) {
    return key.toString();
  }
}
