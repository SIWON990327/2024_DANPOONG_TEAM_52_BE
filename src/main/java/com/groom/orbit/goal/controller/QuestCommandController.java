package com.groom.orbit.goal.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
