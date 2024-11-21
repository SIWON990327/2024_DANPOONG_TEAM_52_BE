package com.groom.orbit.goal.app.dto;

import java.time.LocalDate;

public record CreateQuestRequestDto(Long goalId, String title, LocalDate deadline) {}
