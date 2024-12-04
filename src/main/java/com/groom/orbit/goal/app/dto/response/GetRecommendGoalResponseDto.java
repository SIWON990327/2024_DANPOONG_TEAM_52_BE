package com.groom.orbit.goal.app.dto.response;

import java.util.List;

public record GetRecommendGoalResponseDto(
    Long goalId, String title, String category, List<String> descriptions) {}
