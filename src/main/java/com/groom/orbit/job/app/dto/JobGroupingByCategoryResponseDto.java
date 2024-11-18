package com.groom.orbit.job.app.dto;

import java.util.List;
import java.util.Map;

public record JobGroupingByCategoryResponseDto(Map<String, List<JobDetailResponseDto>> jobs) {}
