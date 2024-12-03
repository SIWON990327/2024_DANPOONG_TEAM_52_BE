package com.groom.orbit.ai.app.dto;

import lombok.Builder;

@Builder
public record UpdateVectorQuestDto(Long memberId, String quest, String newQuest) {}
