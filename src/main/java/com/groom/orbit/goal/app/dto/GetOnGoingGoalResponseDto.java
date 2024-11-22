package com.groom.orbit.goal.app.dto;

public record GetOnGoingGoalResponseDto(
    Long goalId, String title, long totalQuestCount, long finishQuestCount) {}
