package com.groom.orbit.goal.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groom.orbit.goal.dao.entity.Goal;
import com.groom.orbit.goal.dao.entity.GoalCategory;

public interface GoalRepository extends JpaRepository<Goal, Long> {

  Optional<Goal> findByTitleAndCategory(String title, GoalCategory category);
}
