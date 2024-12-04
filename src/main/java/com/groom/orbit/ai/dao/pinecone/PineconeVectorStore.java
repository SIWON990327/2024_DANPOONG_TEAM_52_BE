package com.groom.orbit.ai.dao.pinecone;

import static com.groom.orbit.ai.app.util.PineconeConst.INDEX_NAME;
import static com.groom.orbit.ai.app.util.PineconeConst.INTEREST_JOB_NAMESPACE;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.google.protobuf.Struct;
import com.groom.orbit.ai.app.util.PineconeVectorMapper;
import com.groom.orbit.ai.dao.VectorStore;
import com.groom.orbit.ai.dao.vector.Vector;

import io.pinecone.clients.Index;
import io.pinecone.clients.Pinecone;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;

@Component
public class PineconeVectorStore implements VectorStore {

  private final Index index;
  private final PineconeVectorMapper mapper;

  private static final int SIMILAR_VECTOR_COUNT = 11;
  private static final int FIND_VECTOR_COUNT = 1;

  public PineconeVectorStore(Pinecone pinecone, PineconeVectorMapper mapper) {
    this.index = pinecone.getIndexConnection(INDEX_NAME);
    this.mapper = mapper;
  }

  public void save(Long key, List<Float> vectors, Struct metadata) {
    upsert(key, vectors, metadata);
  }

  public Optional<Vector> findById(Long id) {
    String findKey = getId(id);
    QueryResponseWithUnsignedIndices response = getByVectorId(findKey);

    return response.getMatchesList().stream()
        .filter(match -> findKey.equals(match.getId()))
        .findFirst()
        .map(match -> mapper.fromStruct(match.getMetadata()));
  }

  public List<Vector> findSimilar(List<Float> vector) {
    QueryResponseWithUnsignedIndices response = getQueryByVector(vector);

    return response.getMatchesList().stream()
        .map(match -> mapper.fromStruct(match.getMetadata()))
        .toList();
  }

  private QueryResponseWithUnsignedIndices getQueryByVector(List<Float> vector) {
    return index.queryByVector(
        SIMILAR_VECTOR_COUNT, vector, INTEREST_JOB_NAMESPACE, null, false, true);
  }

  private QueryResponseWithUnsignedIndices getByVectorId(String findKey) {
    return index.queryByVectorId(FIND_VECTOR_COUNT, findKey, INTEREST_JOB_NAMESPACE, false, true);
  }

  private void upsert(Long key, List<Float> vector, Struct metadata) {
    index.upsert(getId(key), vector, null, null, metadata, INTEREST_JOB_NAMESPACE);
  }

  private String getId(Long id) {
    return id.toString();
  }
}
