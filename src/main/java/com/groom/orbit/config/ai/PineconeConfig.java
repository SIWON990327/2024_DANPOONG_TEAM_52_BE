package com.groom.orbit.config.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.pinecone.clients.Pinecone;
import io.pinecone.clients.Pinecone.Builder;

@Configuration
public class PineconeConfig {

  @Value("${spring.ai.vectorstore.pinecone.api-key}")
  private String pineconeApiKey;

  @Bean
  public Pinecone pinecone() {
    return new Builder(pineconeApiKey).build();
  }
}
