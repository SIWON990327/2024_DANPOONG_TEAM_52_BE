package com.groom.orbit.ai.app.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record CreateVectorDto(
    Long memberId, String memberName, List<String> interestJobs, String goal, String quest) {}
