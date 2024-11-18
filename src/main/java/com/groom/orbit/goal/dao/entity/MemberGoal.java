package com.groom.orbit.goal.dao.entity;

import com.groom.orbit.member.dao.jpa.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    public static MemberGoal create(Member member, Goal goal) {
        MemberGoal memberGoal = new MemberGoal();
        memberGoal.member = member;
        memberGoal.goal = goal;

        return memberGoal;
    }
}
