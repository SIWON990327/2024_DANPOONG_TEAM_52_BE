package com.groom.orbit.goal.app.query;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;
import com.groom.orbit.goal.app.MemberGoalService;
import com.groom.orbit.goal.app.dto.response.GoalSearchDetailResponseDto;
import com.groom.orbit.goal.dao.entity.Goal;
import com.groom.orbit.goal.dao.entity.MemberGoal;
import com.groom.orbit.goal.dao.entity.Quest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GoalSearchService {

  private final MemberGoalService memberGoalService;

  public GoalSearchDetailResponseDto findGoal(Long goalId) {
    List<MemberGoal> memberGoals = memberGoalService.findAllMemberGoal(goalId);
    Goal goal = findGoal(memberGoals);
    List<String> questTitles =
        memberGoals.stream()
            .map(MemberGoal::getQuests)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet())
            .stream()
            .sorted(Comparator.comparing(Quest::getCreatedAt).reversed())
            .map(Quest::getTitle)
            .toList();

    return new GoalSearchDetailResponseDto(
        goal.getCategory().getCategory(), goal.getTitle(), questTitles);
  }

  private static Goal findGoal(List<MemberGoal> memberGoals) {
    if (memberGoals.isEmpty()) {
      throw new CommonException(ErrorCode.NOT_FOUND_GOAL);
    }
    return memberGoals.getFirst().getGoal();
  }
}
