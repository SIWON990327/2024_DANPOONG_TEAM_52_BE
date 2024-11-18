package com.groom.orbit.goal.dao;

import com.groom.orbit.goal.dao.entity.MemberGoal;
import com.groom.orbit.goal.dao.entity.MemberGoalId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberGoalRepository extends JpaRepository<MemberGoal, MemberGoalId> {

}
