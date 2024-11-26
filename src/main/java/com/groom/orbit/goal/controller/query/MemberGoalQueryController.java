package com.groom.orbit.goal.controller.query;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.app.MemberGoalService;
import com.groom.orbit.goal.app.dto.response.GetMemberGoalResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal")
public class MemberGoalQueryController {

  private final MemberGoalService memberGoalService;

  @GetMapping
  public ResponseDto<List<GetMemberGoalResponseDto>> getGoals(
      @AuthMember Long memberId,
      @RequestParam(value = "is_complete", required = false) Boolean isComplete) {
    return ResponseDto.ok(memberGoalService.findGoals(memberId, isComplete));
  }

  @GetMapping("/{member_goal_id}")
  public ResponseDto<GetMemberGoalResponseDto> getGoal(
      @PathVariable("member_goal_id") Long memberGoalId) {
    return ResponseDto.ok(memberGoalService.findGoal(memberGoalId));
  }

  @GetMapping("/recommend")
  public ResponseDto<?> getRecommendedGoals(@AuthMember Long memberId) {
    return ResponseDto.ok(memberGoalService.findMemberGoalNotCompleted(memberId));
  }
}
