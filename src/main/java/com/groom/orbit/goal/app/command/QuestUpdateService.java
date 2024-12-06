package com.groom.orbit.goal.app.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.ai.app.VectorService;
import com.groom.orbit.ai.app.dto.UpdateVectorQuestDto;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.goal.app.dto.request.UpdateQuestRequestDto;
import com.groom.orbit.goal.app.query.QuestQueryService;
import com.groom.orbit.goal.dao.entity.Quest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestUpdateService {

  private final QuestQueryService questQueryService;
  private final VectorService vectorService;

  public CommonSuccessDto updateQuest(Long memberId, Long questId, UpdateQuestRequestDto dto) {
    Quest quest = questQueryService.findQuest(questId);
    quest.validateMember(memberId);

    updateVector(memberId, dto, quest);
    quest.update(dto.title(), dto.isComplete(), dto.deadline());

    return new CommonSuccessDto(true);
  }

  private void updateVector(Long memberId, UpdateQuestRequestDto dto, Quest quest) {
    UpdateVectorQuestDto updateDto =
        UpdateVectorQuestDto.builder()
            .memberId(memberId)
            .quest(quest.getTitle())
            .newQuest(dto.title())
            .build();
    vectorService.updateQuest(updateDto);
  }
}
