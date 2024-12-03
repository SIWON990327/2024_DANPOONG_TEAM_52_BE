package com.groom.orbit.config.ai;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.openapitools.inference.client.ApiException;
import org.openapitools.inference.client.model.Embedding;
import org.openapitools.inference.client.model.EmbeddingsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.pinecone.clients.Inference;
import io.pinecone.clients.Pinecone;

@SpringBootTest
class PineconeConfigTest {

  @Autowired Pinecone pinecone;

  @Test
  void pinconeInitTest() throws Exception {
    // given
    Inference inference = pinecone.getInferenceClient();
    String embeddingModel = "multilingual-e5-large";

    Map<String, Object> parameters = Map.of("input_type", "query", "truncate", "END");
    List<String> inputs = new ArrayList<>();
    inputs.add("The quick brown fox jumps over the lazy dog.");
    inputs.add("Lorem ipsum");

    EmbeddingsList embeddings = getEmbeddingList(inference, embeddingModel, parameters, inputs);
    List<Embedding> embeddedData = embeddings.getData();
    List<List<Float>> floatLists =
        embeddedData.stream()
            .map(Embedding::getValues)
            .filter(Objects::nonNull)
            .map(values -> values.stream().map(BigDecimal::floatValue).toList())
            .toList();
  }

  private static EmbeddingsList getEmbeddingList(
      Inference inference,
      String embeddingModel,
      Map<String, Object> parameters,
      List<String> inputs) {
    try {
      return inference.embed(embeddingModel, parameters, inputs);
    } catch (ApiException e) {
      throw new RuntimeException(e);
    }
  }
}
