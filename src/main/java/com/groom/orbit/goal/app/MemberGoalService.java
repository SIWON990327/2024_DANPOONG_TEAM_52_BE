package com.groom.orbit.goal.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;
import com.groom.orbit.goal.dao.MemberGoalRepository;
import com.groom.orbit.goal.dao.entity.MemberGoal;
import com.groom.orbit.goal.dao.entity.MemberGoalId;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberGoalService {

  private final MemberGoalRepository memberGoalRepository;

  @Transactional(readOnly = true)
  public MemberGoal findMemberGoal(Long memberId, Long goalId) {
    MemberGoalId memberGoalId = MemberGoalId.create(memberId, goalId);

    return memberGoalRepository
        .findById(memberGoalId.getMemberId(), memberGoalId.getGoalId())
        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_GOAL));
  }
}
