package com.groom.orbit.goal.app.command;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.goal.dao.GoalRepository;
import com.groom.orbit.goal.dao.entity.Goal;
import com.groom.orbit.goal.dao.entity.GoalCategory;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GoalCommandService {

  private final GoalRepository goalRepository;

  public Goal createGoal(String title, String category) {
    Goal goal = Goal.create(title, category);

    return goalRepository.save(goal);
  }

  public Goal upsert(String title, String category) {
    Optional<Goal> optionalGoal =
        goalRepository.findByTitleAndCategory(title, GoalCategory.from(category));
    return optionalGoal.orElseGet(() -> createGoal(title, category));
  }
}
