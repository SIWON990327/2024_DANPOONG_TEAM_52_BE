package com.groom.orbit.job.app;

import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import com.groom.orbit.job.app.dto.GetRelatedInterestJob;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindRelatedMemberService {

  private final ChatModel chatModel;
  private final InterestJobService interestJobService;

  private String callChatModel(PromptTemplate promptTemplate, Map<String, Object> variables) {
    Prompt prompt = promptTemplate.create(variables);
    ChatResponse response = chatModel.call(prompt);

    return response.getResult().getOutput().getContent();
  }

  public GetRelatedInterestJob findRelatedMembersInterestJob(Long memberId) {
    return null;
  }
}
