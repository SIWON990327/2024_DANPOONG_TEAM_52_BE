package com.groom.orbit.goal.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;
import com.groom.orbit.member.dao.jpa.entity.Member;

import lombok.Getter;

@Entity
@Getter
@Table(name = "member_goal")
@IdClass(MemberGoalId.class)
public class MemberGoal {

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "goal_id")
  private Goal goal;

  @ColumnDefault("false")
  @Column(nullable = false)
  private Boolean isComplete;

  @Column(name = "member_id", insertable = false, updatable = false)
  private Long memberId;

  @Column(name = "goal_id", insertable = false, updatable = false)
  private Long goalId;

  public static MemberGoal create(Member member, Goal goal) {
    MemberGoal memberGoal = new MemberGoal();
    memberGoal.member = member;
    memberGoal.goal = goal;

    return memberGoal;
  }

  public void validateMember(Member member) {
    if (!memberId.equals(member.getId())) {
      throw new CommonException(ErrorCode.ACCESS_DENIED);
    }
  }
}
