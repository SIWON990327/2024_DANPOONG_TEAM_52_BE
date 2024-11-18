package com.groom.orbit.goal.app;

import java.util.List;

import org.springframework.stereotype.Service;

import com.groom.orbit.goal.app.dto.QuestInfoResponseDto;
import com.groom.orbit.goal.dao.QuestRepository;
import com.groom.orbit.goal.dao.entity.Quest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestQueryService {

  private final QuestRepository questRepository;

  public List<QuestInfoResponseDto> findQuestsByGoalId(Long memberId, Long goalId) {
    List<Quest> quests = questRepository.findByMemberIdAndGoalId(memberId, goalId);

    return quests.stream()
        .map(
            quest ->
                new QuestInfoResponseDto(
                    quest.getQuestId(), quest.getTitle(), quest.getIsComplete()))
        .toList();
  }
}
