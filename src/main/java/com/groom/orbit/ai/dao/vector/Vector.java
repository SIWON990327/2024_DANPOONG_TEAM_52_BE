package com.groom.orbit.ai.dao.vector;

import java.util.List;

import lombok.Builder;

@Builder
public record Vector(
    Long memberId,
    String memberName,
    List<String> interestJobs,
    List<String> goals,
    List<String> quests) {}
