package com.groom.orbit.goal.app;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;
import com.groom.orbit.goal.app.dto.CreateQuestRequestDto;
import com.groom.orbit.goal.app.dto.UpdateQuestRequestDto;
import com.groom.orbit.goal.app.dto.UpdateQuestSequenceRequestDto;
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

  /** select 최적화 */
  public CommonSuccessDto deleteQuest(Long memberId, Long questId, Long goalId) {
    List<Quest> quests = questQueryService.findQuestsByMemberAndGoal(memberId, goalId);
    Quest removeQuest =
        quests.stream()
            .filter(q -> q.getQuestId().equals(questId))
            .findFirst()
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_QUEST));
    Integer removeSequence = removeQuest.getSequence();
    updateSequence(quests, removeSequence);

    questRepository.delete(removeQuest);

    return CommonSuccessDto.fromEntity(true);
  }

  public CommonSuccessDto updateQuest(Long memberId, Long questId, UpdateQuestRequestDto dto) {
    Quest quest = questQueryService.findQuest(questId);
    quest.validateMember(memberId);

    quest.update(dto.title(), dto.isComplete(), dto.deadline());

    return new CommonSuccessDto(true);
  }

  private static void updateSequence(List<Quest> quests, Integer removeSequence) {
    for (Quest quest : quests) {
      if (quest.getSequence() > removeSequence) {
        quest.decreaseSequence();
      }
    }
  }

  public CommonSuccessDto updateQuestSequence(
      Long memberId, List<UpdateQuestSequenceRequestDto> dtos) {
    List<Long> questIds = dtos.stream().map(UpdateQuestSequenceRequestDto::questId).toList();
    List<Quest> sortedQuest =
        questRepository.findByQuestIdIn(questIds).stream()
            .sorted(Comparator.comparingLong(Quest::getQuestId))
            .toList();

    validateQuests(memberId, sortedQuest);

    for (UpdateQuestSequenceRequestDto dto : dtos) {
      Quest findQuest = findQuestInSortedByQuestId(sortedQuest, dto.questId());
      findQuest.updateSequence(dto.sequence());
    }

    return CommonSuccessDto.fromEntity(true);
  }

  private static void validateQuests(Long memberId, List<Quest> sortedQuest) {
    sortedQuest.forEach(quest -> quest.validateMember(memberId));
  }

  private Quest findQuestInSortedByQuestId(List<Quest> quests, Long questId) {
    int left = 0, right = quests.size() - 1;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      Quest quest = quests.get(mid);
      if (quest.compareWithId(questId) == 0) {
        return quest;
      } else if (quest.compareWithId(questId) > 0) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }

    throw new CommonException(ErrorCode.NOT_FOUND_QUEST);
  }
}
