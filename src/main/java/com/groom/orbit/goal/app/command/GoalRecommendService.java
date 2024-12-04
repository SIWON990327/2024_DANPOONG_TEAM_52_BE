package com.groom.orbit.goal.app.command;

import org.springframework.stereotype.Service;

import com.groom.orbit.ai.app.AiService;
import com.groom.orbit.goal.app.dto.request.CreateGoalRequestDto;
import com.groom.orbit.goal.app.dto.response.GetRecommendGoalResponseDto;
import com.groom.orbit.goal.dao.entity.Goal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoalRecommendService {

  private final GoalCommandService goalCommandService;
  private final AiService aiService;

  public GetRecommendGoalResponseDto createRecommendGoal(Long memberId) {
    CreateGoalRequestDto dto = aiService.recommendGoal(memberId);
    Goal goal = goalCommandService.upsert(dto.title(), dto.category());

    return new GetRecommendGoalResponseDto(goal.getGoalId(), dto.title(), dto.category());
  }
}
