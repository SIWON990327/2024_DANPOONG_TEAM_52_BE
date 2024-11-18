package com.groom.orbit.goal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.app.QuestQueryService;
import com.groom.orbit.goal.app.dto.QuestInfoResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quest")
public class QuestQueryController {

  private final QuestQueryService questQueryService;

  /** TODO add annotation */
  @GetMapping
  public ResponseDto<List<QuestInfoResponseDto>> findQuest(
      Long memberId, @RequestParam("goal_id") Long goalId) {
    return ResponseDto.ok(questQueryService.findQuestsByGoalId(memberId, goalId));
  }
}
