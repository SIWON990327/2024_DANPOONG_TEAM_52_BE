package com.groom.orbit.ai.app.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record MemberInfoDto(
    Long memberId,
    String memberName,
    List<String> interestJobs,
    List<String> goals,
    List<String> quests) {}
