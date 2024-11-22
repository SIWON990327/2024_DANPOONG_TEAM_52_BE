package com.groom.orbit.goal.app;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.goal.app.dto.QuestInfoResponseDto;
import com.groom.orbit.goal.dao.QuestRepository;
import com.groom.orbit.goal.dao.entity.Quest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestQueryService {

  private final QuestRepository questRepository;

  public List<Quest> findQuestsByMemberAndGoal(Long memberId, Long goalId) {
    return questRepository.findByMemberIdAndGoalId(memberId, goalId);
  }

  public List<QuestInfoResponseDto> findQuestsByGoalId(Long memberId, Long goalId) {
    List<Quest> quests = questRepository.findByMemberIdAndGoalId(memberId, goalId);

    return quests.stream()
        .map(
            quest ->
                new QuestInfoResponseDto(
                    quest.getQuestId(), quest.getTitle(), quest.getIsComplete()))
        .toList();
  }

  public int getQuestCountsByGoalId(Long goalId) {
    return questRepository.getCountByGoalId(goalId);
  }
}
