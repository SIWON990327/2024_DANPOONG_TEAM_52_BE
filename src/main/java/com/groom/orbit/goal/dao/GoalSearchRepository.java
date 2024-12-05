package com.groom.orbit.goal.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groom.orbit.goal.dao.entity.Goal;

public interface GoalSearchRepository extends JpaRepository<Goal, Long> {}
