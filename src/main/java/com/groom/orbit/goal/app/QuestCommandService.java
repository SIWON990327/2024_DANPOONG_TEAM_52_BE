package com.groom.orbit.goal.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.goal.app.dto.CreateQuestRequestDto;
import com.groom.orbit.goal.dao.QuestRepository;
import com.groom.orbit.goal.dao.entity.MemberGoal;
import com.groom.orbit.goal.dao.entity.Quest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestCommandService {

  private final MemberGoalService memberGoalService;
  private final QuestQueryService questQueryService;
  private final QuestRepository questRepository;

  /** TODO join 최적화 */
  public CommonSuccessDto createQuest(Long memberId, CreateQuestRequestDto dto) {
    MemberGoal memberGoal = memberGoalService.findMemberGoal(memberId, dto.goalId());
    int newQuestSequence = questQueryService.getQuestCountsByGoalId(dto.goalId()) + 1;
    Quest quest = Quest.create(dto.title(), memberGoal, dto.deadline(), newQuestSequence);

    questRepository.save(quest);

    return CommonSuccessDto.fromEntity(true);
  }
}
