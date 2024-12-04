package com.groom.orbit.goal.controller.command;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.app.MemberGoalService;
import com.groom.orbit.goal.app.dto.request.MemberGoalRequestDto;
import com.groom.orbit.goal.app.dto.request.UpdateMemberGoalSequenceRequestDto;
import com.groom.orbit.goal.app.dto.response.GetMemberGoalResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal")
public class MemberGoalCommandController {

  private final MemberGoalService memberGoalService;

  @DeleteMapping("/{member_goal_id}")
  public ResponseDto<CommonSuccessDto> deleteMemberGoal(
      @AuthMember Long memberId, @PathVariable("member_goal_id") Long memberGoalId) {
    return ResponseDto.ok(memberGoalService.deleteMemberGoal(memberId, memberGoalId));
  }

  @PostMapping
  public ResponseDto<GetMemberGoalResponseDto> createMemberGoal(
      @AuthMember Long memberId, @RequestBody MemberGoalRequestDto dto) {
    return ResponseDto.created(memberGoalService.createGoal(memberId, dto));
  }

  @PatchMapping("/{member_goal_id}")
  public ResponseDto<CommonSuccessDto> updateMemberGoal(
      @AuthMember Long memberId,
      @PathVariable("member_goal_id") Long memberGoalId,
      @RequestBody MemberGoalRequestDto dto) {
    return ResponseDto.ok(memberGoalService.updateMemberGoal(memberId, memberGoalId, dto));
  }

  @PatchMapping
  public ResponseDto<CommonSuccessDto> updateMemberGoalSequence(
      @AuthMember Long memberId,
      @RequestBody List<UpdateMemberGoalSequenceRequestDto> requestDtoList) {
    return ResponseDto.ok(memberGoalService.updateMemberGoalSequence(memberId, requestDtoList));
  }

  @PatchMapping("/complete/{member_goal_id}")
  public ResponseDto<CommonSuccessDto> updateMemberGoalIsComplete(
      @AuthMember Long memberId, @PathVariable("member_goal_id") Long memberGoalId) {
    return ResponseDto.ok(memberGoalService.updateMemberGoalIsComplete(memberId, memberGoalId));
  }

  @PostMapping("/copy/{member_goal_id}")
  public ResponseDto<GetMemberGoalResponseDto> copyMemberGoal(
      @AuthMember Long memberId, @PathVariable("member_goal_id") Long otherMemberGoalId) {
    return ResponseDto.ok(memberGoalService.createOtherGoal(memberId, otherMemberGoalId));
  }
}
