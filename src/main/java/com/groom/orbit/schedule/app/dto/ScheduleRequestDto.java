package com.groom.orbit.schedule.app.dto;

import java.util.Date;

public record ScheduleRequestDto(String content, Date startDate, Date endDate) {}
