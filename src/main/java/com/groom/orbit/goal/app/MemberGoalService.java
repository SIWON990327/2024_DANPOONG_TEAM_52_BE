package com.groom.orbit.goal.app;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;
import com.groom.orbit.goal.app.command.GoalCommandService;
import com.groom.orbit.goal.app.dto.request.MemberGoalRequestDto;
import com.groom.orbit.goal.app.dto.response.GetCompletedGoalResponseDto;
import com.groom.orbit.goal.app.dto.response.GetOnGoingGoalResponseDto;
import com.groom.orbit.goal.app.query.GoalQueryService;
import com.groom.orbit.goal.app.query.QuestQueryService;
import com.groom.orbit.goal.dao.MemberGoalRepository;
import com.groom.orbit.goal.dao.entity.Goal;
import com.groom.orbit.goal.dao.entity.MemberGoal;
import com.groom.orbit.goal.dao.entity.Quest;
import com.groom.orbit.member.app.MemberQueryService;
import com.groom.orbit.member.dao.jpa.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberGoalService {

  private final MemberGoalRepository memberGoalRepository;
  private final QuestQueryService questQueryService;
  private final MemberQueryService memberQueryService;
  private final GoalQueryService goalQueryService;
  private final GoalCommandService goalCommandService;

  @Transactional(readOnly = true)
  public MemberGoal findMemberGoal(Long memberId, Long goalId) {

    return memberGoalRepository
        .findById(memberId, goalId)
        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_GOAL));
  }

  public List<GetOnGoingGoalResponseDto> findOnGoingGoals(Long memberId) {
    List<MemberGoal> memberGoals = memberGoalRepository.findByIsComplete(memberId, false);

    return memberGoals.stream()
        .map(
            memberGoal -> {
              Long goalId = memberGoal.getGoal().getGoalId();
              long totalQuestCount = questQueryService.getTotalQuestCount(goalId);
              long finishQuestCount = questQueryService.getFinishQuestCount(goalId);

              return new GetOnGoingGoalResponseDto(
                  goalId, memberGoal.getTitle(), totalQuestCount, finishQuestCount);
            })
        .toList();
  }

  public List<GetCompletedGoalResponseDto> findCompletedGoals(Long memberId) {
    List<MemberGoal> memberGoals = memberGoalRepository.findByIsComplete(memberId, true);

    return memberGoals.stream()
        .map(
            memberGoal ->
                new GetCompletedGoalResponseDto(
                    memberGoal.getTitle(),
                    memberGoal.getQuests().stream().map(Quest::getTitle).toList()))
        .toList();
  }

  public CommonSuccessDto deleteGoal(Long memberId, Long goalId) {
    MemberGoal memberGoal = findMemberGoal(memberId, goalId);
    Goal goal = memberGoal.getGoal();

    goal.decreaseCount();
    memberGoalRepository.delete(memberGoal);

    return new CommonSuccessDto(true);
  }

  public CommonSuccessDto createGoal(Long memberId, MemberGoalRequestDto dto) {
    Member member = memberQueryService.findMember(memberId);
    Goal goal = getGoal(dto.title(), dto.category());
    MemberGoal memberGoal = MemberGoal.create(member, goal);
    goal.increaseCount();

    memberGoalRepository.save(memberGoal);

    return new CommonSuccessDto(true);
  }

  public CommonSuccessDto updateGoal(Long memberId, Long goalId, MemberGoalRequestDto dto) {
    MemberGoal memberGoal = findMemberGoal(memberId, goalId);
    Goal goal = getGoal(dto.title(), dto.category());

    validateMemberGoal(memberId, goal.getGoalId());
    memberGoal.updateGoal(goal);

    //    memberGoalRepository.updateGoalId(goal.getGoalId());
    return new CommonSuccessDto(true);
  }

  private void validateMemberGoal(Long memberId, Long goalId) {
    if (memberGoalRepository.findByMemberIdAndGoalId(memberId, goalId).isPresent()) {
      throw new CommonException(ErrorCode.ALREADY_EXISTS_GOAL);
    }
  }

  private Goal getGoal(String title, String category) {
    Optional<Goal> findGoal = goalQueryService.findGoalByTitleAndCategory(title, category);
    return findGoal.orElseGet(() -> goalCommandService.createGoal(title, category));
  }
}
