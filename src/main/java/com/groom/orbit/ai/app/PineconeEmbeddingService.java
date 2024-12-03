package com.groom.orbit.ai.app;

import static com.groom.orbit.ai.app.util.PineconeConst.EMBEDDING_MODEL;
import static com.groom.orbit.ai.app.util.PineconeConst.INPUT_TYPE_PARAMETER_KEY;
import static com.groom.orbit.ai.app.util.PineconeConst.QUERY_PARAMETER_VALUE;

import java.util.List;
import java.util.Map;

import org.openapitools.inference.client.ApiException;
import org.openapitools.inference.client.model.Embedding;
import org.springframework.stereotype.Service;

import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;

import io.pinecone.clients.Inference;
import io.pinecone.clients.Pinecone;

@Service
public class PineconeEmbeddingService {

  private final Pinecone pinecone;
  private final Map<String, Object> parameters;

  public PineconeEmbeddingService(Pinecone pinecone) {
    this.pinecone = pinecone;
    this.parameters = Map.of(INPUT_TYPE_PARAMETER_KEY, QUERY_PARAMETER_VALUE);
  }

  public List<Embedding> embed(List<String> inputs) {
    Inference inference = pinecone.getInferenceClient();
    try {
      return inference.embed(EMBEDDING_MODEL, parameters, inputs).getData();
    } catch (ApiException e) {
      throw new CommonException(ErrorCode.INVALID_VECTORSTORE_STATE);
    }
  }
}
