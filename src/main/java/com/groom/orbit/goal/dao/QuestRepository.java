package com.groom.orbit.goal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.groom.orbit.goal.dao.entity.Quest;

public interface QuestRepository extends JpaRepository<Quest, Long> {

  @Query(
      "select q from Quest q"
          + " join fetch q.memberGoal mg"
          + " where mg.memberId=:member_id and mg.goalId=:goal_id")
  List<Quest> findByMemberIdAndGoalId(
      @Param("member_id") Long memberId, @Param("goal_id") Long goalId);

  @Query("select count(*) from Quest q" + " join q.memberGoal mg" + " where mg.goalId=:goal_id")
  int getCountByGoalId(@Param("goal_id") Long goalId);

  List<Quest> findByQuestIdIn(List<Long> ids);

  @Query("select count(*) from Quest q" + " join q.memberGoal mg" + " where mg.goalId=:goal_id")
  long countByMemberGoal_GoalId(@Param("goal_id") Long goalId);

  @Query(
      "select count(*) from Quest q"
          + " join q.memberGoal mg"
          + " where q.isComplete=true and mg.goalId=:goal_id")
  long countCompletedByMemberGoal_GoalId(@Param("goal_id") Long goalId);

  @Query(
      "SELECT q FROM Quest q join fetch q.memberGoal mg WHERE MONTH(q.deadline) = :month AND mg.memberId = :memberId ORDER BY q.deadline ASC")
  List<Quest> findAllByMonthAndMemberId(Long memberId, Integer month);
}
