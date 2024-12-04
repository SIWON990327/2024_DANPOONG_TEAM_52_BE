package com.groom.orbit.goal.app.command;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.groom.orbit.ai.app.AiService;
import com.groom.orbit.goal.app.dto.response.GetRecommendGoalListResponseDto;
import com.groom.orbit.goal.app.dto.response.GetRecommendGoalResponseDto;
import com.groom.orbit.goal.app.dto.response.RecommendGoalListResponseDto;
import com.groom.orbit.goal.app.dto.response.RecommendGoalResponseDto;
import com.groom.orbit.goal.dao.entity.Goal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoalRecommendService {

  private final GoalCommandService goalCommandService;
  private final AiService aiService;

  public GetRecommendGoalListResponseDto createRecommendGoal(Long memberId) {
    RecommendGoalListResponseDto dtos = aiService.recommendGoal(memberId);
    dtos.items().forEach(dto -> goalCommandService.upsert(dto.title(), dto.category()));
    List<Goal> newGoals =
        dtos.items().stream()
            .map(dto -> goalCommandService.upsert(dto.title(), dto.category()))
            .toList();

    return new GetRecommendGoalListResponseDto(convertToDto(newGoals, dtos));
  }

  private static List<GetRecommendGoalResponseDto> convertToDto(
      List<Goal> newGoals, RecommendGoalListResponseDto dtos) {
    return IntStream.range(0, newGoals.size())
        .mapToObj(
            i -> {
              RecommendGoalResponseDto dto = dtos.items().get(i);
              return new GetRecommendGoalResponseDto(
                  newGoals.get(i).getGoalId(), dto.title(), dto.category(), dto.descriptions());
            })
        .toList();
  }
}
