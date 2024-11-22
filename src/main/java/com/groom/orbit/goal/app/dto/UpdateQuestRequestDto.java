package com.groom.orbit.goal.app.dto;

import java.time.LocalDate;

public record UpdateQuestRequestDto(String title, Boolean isComplete, LocalDate deadline) {}
