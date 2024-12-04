package com.groom.orbit.goal.app.command;

import org.springframework.stereotype.Service;

import com.groom.orbit.ai.app.AiService;
import com.groom.orbit.goal.app.dto.response.RecommendQuestResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestRecommendService {

  private final AiService aiService;

  public RecommendQuestResponseDto recommendQuest(Long memberId) {
    return aiService.recommendQuest(memberId);
  }
}
