package com.groom.orbit.goal.controller.command;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.app.command.GoalRecommendService;
import com.groom.orbit.goal.app.dto.response.GetRecommendGoalResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal")
public class GoalRecommendController {

  private final GoalRecommendService goalRecommendService;

  @PostMapping("/recommend")
  public ResponseDto<GetRecommendGoalResponseDto> creatRecommendGoal(@AuthMember Long memberId) {
    return ResponseDto.ok(goalRecommendService.createRecommendGoal(memberId));
  }
}
