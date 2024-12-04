package com.groom.orbit.goal.controller.command;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.app.command.QuestRecommendService;
import com.groom.orbit.goal.app.dto.response.RecommendQuestListResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quest")
public class QuestRecommendController {

  private final QuestRecommendService questRecommendService;

  @PostMapping("/recommend")
  public ResponseDto<RecommendQuestListResponseDto> recommendQuest(@AuthMember Long memberId) {
    return ResponseDto.ok(questRecommendService.recommendQuest(memberId));
  }
}
