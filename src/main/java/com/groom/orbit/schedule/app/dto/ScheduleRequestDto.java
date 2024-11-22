package com.groom.orbit.schedule.app.dto;

import java.time.LocalDate;

public record ScheduleRequestDto(String content, LocalDate startDate, LocalDate endDate) {}
