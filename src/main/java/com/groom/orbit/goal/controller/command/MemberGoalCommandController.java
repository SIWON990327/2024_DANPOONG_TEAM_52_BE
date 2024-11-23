package com.groom.orbit.goal.controller.command;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.app.MemberGoalService;
import com.groom.orbit.goal.app.dto.request.CreateMemberGoalRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal")
public class MemberGoalCommandController {

  private final MemberGoalService memberGoalService;

  @DeleteMapping("/{goal_id}")
  public ResponseDto<CommonSuccessDto> deleteMemberGoal(
      @AuthMember Long memberId, @PathVariable("goal_id") Long goalId) {
    return ResponseDto.ok(memberGoalService.deleteGoal(memberId, goalId));
  }

  @PutMapping("/{goal_id}")
  public ResponseDto<CommonSuccessDto> createMemberGoal(
      @AuthMember Long memberId,
      @PathVariable("goal_id") Long goalId,
      @RequestBody CreateMemberGoalRequestDto dto) {
    return ResponseDto.created(memberGoalService.createGoal(memberId, goalId, dto));
  }
}
