package com.groom.orbit.goal.dao.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MemberGoalId implements Serializable {
  private Long memberId;
  private Long goalId;

  public static MemberGoalId create(Long memberId, Long goalId) {
    MemberGoalId memberGoalId = new MemberGoalId();
    memberGoalId.memberId = memberId;
    memberGoalId.goalId = goalId;

    return memberGoalId;
  }
}
