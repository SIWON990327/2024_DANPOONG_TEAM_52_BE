package com.groom.orbit.goal.app.command;

import org.springframework.stereotype.Service;

import com.groom.orbit.ai.app.AiService;
import com.groom.orbit.goal.app.MemberGoalService;
import com.groom.orbit.goal.app.dto.request.CreateGoalRequestDto;
import com.groom.orbit.goal.app.dto.response.RecommendGoalResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoalRecommendService {

  private final MemberGoalService memberGoalService;
  private final GoalCommandService goalCommandService;
  private final AiService aiService;

  private static final String GOAL_JOIN_DELIMITER = ",";

  public RecommendGoalResponseDto createRecommendGoal(Long memberId) {
    String goalList =
        String.join(GOAL_JOIN_DELIMITER, memberGoalService.findMemberGoalNotCompleted(memberId));

    CreateGoalRequestDto dto = aiService.recommendGoal(memberId, goalList);
    goalCommandService.createGoal(dto.title(), dto.category());

    return new RecommendGoalResponseDto(dto.title(), dto.category());
  }
}
