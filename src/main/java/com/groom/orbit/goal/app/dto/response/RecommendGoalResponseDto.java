package com.groom.orbit.goal.app.dto.response;

import java.util.List;

public record RecommendGoalResponseDto(String title, String category, List<String> descriptions) {}
