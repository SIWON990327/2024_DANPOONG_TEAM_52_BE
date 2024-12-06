package com.groom.orbit.goal.app.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.ai.app.VectorService;
import com.groom.orbit.ai.app.dto.CreateVectorDto;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.goal.app.MemberGoalService;
import com.groom.orbit.goal.app.dto.request.CreateQuestRequestDto;
import com.groom.orbit.goal.app.dto.response.CreateQuestResponse;
import com.groom.orbit.goal.app.query.QuestQueryService;
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
  private final VectorService vectorService;

  /** TODO join 최적화 */
  public CreateQuestResponse createQuest(Long memberId, CreateQuestRequestDto dto) {
    MemberGoal memberGoal = memberGoalService.findMemberGoal(memberId, dto.goalId());
    memberGoal.validateMember(memberId);

    int newQuestSequence = questQueryService.getQuestCountsByGoalId(dto.goalId()) + 1;
    Quest quest = Quest.create(dto.title(), memberGoal, dto.deadline(), newQuestSequence);
    questRepository.save(quest);
    saveVector(memberId, dto);

    return CreateQuestResponse.fromEntity(quest);
  }

  public CommonSuccessDto deleteOneQuest(Long memberId, Long questId) {
    Quest quest = questQueryService.findQuest(questId);

    quest.validateMember(memberId);
    questRepository.delete(quest);

    return CommonSuccessDto.fromEntity(true);
  }

  private void saveVector(Long memberId, CreateQuestRequestDto dto) {
    CreateVectorDto vectorDto =
        CreateVectorDto.builder().memberId(memberId).quest(dto.title()).build();
    vectorService.save(vectorDto);
  }
}
