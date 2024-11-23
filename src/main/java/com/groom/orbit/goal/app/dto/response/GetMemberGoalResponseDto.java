package com.groom.orbit.goal.app.dto.response;

import java.util.List;

public record GetMemberGoalResponseDto(
    Long memberGoalId, String goalTitle, List<GetQuestResponseDto> quests) {}
