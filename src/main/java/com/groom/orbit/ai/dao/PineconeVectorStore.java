package com.groom.orbit.ai.dao;

import static com.groom.orbit.ai.app.util.PineconeConst.INDEX_NAME;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.protobuf.Struct;

import io.pinecone.clients.Index;
import io.pinecone.clients.Pinecone;

@Component
public class PineconeVectorStore {

  private final Index index;

  @Value("${spring.ai.vectorstore.pinecone.api-key}")
  private String PINECONE_API_KEY;

  public PineconeVectorStore(Pinecone pinecone) {
    this.index = pinecone.getIndexConnection(INDEX_NAME);
  }

  public void save(List<Float> vectors, Struct metadata, String namespace) {
    upsert(vectors, metadata, namespace);
  }

  private void upsert(List<Float> vector, Struct metadata, String namespace) {
    index.upsert(getKey(), vector, null, null, metadata, namespace);
  }

  private String getKey() {
    return UUID.randomUUID().toString();
  }
}
