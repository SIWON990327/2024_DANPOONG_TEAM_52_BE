package com.groom.orbit.ai.app.dto;

import java.util.List;

public record MemberInterestJobDto(Long memberId, String memberName, List<String> interestJobs) {}
