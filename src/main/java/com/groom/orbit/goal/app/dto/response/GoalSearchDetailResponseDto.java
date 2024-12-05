package com.groom.orbit.goal.app.dto.response;

import java.util.List;

public record GoalSearchDetailResponseDto(String category, String goalName, List<String> quests) {}
