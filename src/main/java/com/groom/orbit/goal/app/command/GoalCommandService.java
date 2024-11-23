package com.groom.orbit.goal.app.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.goal.dao.GoalRepository;
import com.groom.orbit.goal.dao.entity.Goal;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GoalCommandService {

  private final GoalRepository goalRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Goal createGoal(String title, String category) {
    Goal goal = Goal.create(title, category);

    return goalRepository.save(goal);
  }
}
