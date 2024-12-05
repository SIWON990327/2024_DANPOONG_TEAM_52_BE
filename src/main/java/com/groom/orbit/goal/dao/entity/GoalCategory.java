package com.groom.orbit.goal.dao.entity;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public enum GoalCategory {
  CERTIFICATION("자격·어학·수상"),
  EXPERIENCE("경험·활동·교육"),
  CAREER("경력"),
  ETC("기타");

  private final String category;

  GoalCategory(String category) {
    this.category = category;
  }

  public static GoalCategory from(String category) {
    return Arrays.stream(GoalCategory.values())
        .filter(goalCategory -> goalCategory.name().equalsIgnoreCase(category))
        .findFirst()
        .orElseGet(() -> null);
  }

  public static List<String> getAll() {
    return Arrays.stream(GoalCategory.values()).map(GoalCategory::getCategory).toList();
  }
}
