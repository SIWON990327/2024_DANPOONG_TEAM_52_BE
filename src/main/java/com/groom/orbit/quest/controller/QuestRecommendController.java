package com.groom.orbit.quest.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.quest.app.QuestRecommendService;
import com.groom.orbit.quest.app.dto.response.RecommendQuestListResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quest")
public class QuestRecommendController {

  private final QuestRecommendService questRecommendService;

  @PostMapping("/recommend/{member_goal_id}")
  public ResponseDto<RecommendQuestListResponseDto> recommendQuest(
      @AuthMember Long memberId, @PathVariable("member_goal_id") Long memberGoalId) {
    return ResponseDto.ok(questRecommendService.recommendQuest(memberId, memberGoalId));
  }
}
