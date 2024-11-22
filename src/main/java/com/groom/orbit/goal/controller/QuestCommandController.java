package com.groom.orbit.goal.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.app.QuestCommandService;
import com.groom.orbit.goal.app.dto.CreateQuestRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quest")
public class QuestCommandController {

  private final QuestCommandService questCommandService;

  @PutMapping
  public ResponseDto<CommonSuccessDto> createQuest(
      @AuthMember Long memberId, @RequestBody CreateQuestRequestDto dto) {
    return ResponseDto.created(questCommandService.createQuest(memberId, dto));
  }

  @DeleteMapping("/{quest_id}/goal/{goal_id}")
  public ResponseDto<CommonSuccessDto> deleteQuest(
      @RequestParam("memberId") Long memberId,
      @PathVariable("quest_id") Long questId,
      @PathVariable("goal_id") Long goalId) {
    return ResponseDto.ok(questCommandService.deleteQuest(memberId, questId, goalId));
  }
}
