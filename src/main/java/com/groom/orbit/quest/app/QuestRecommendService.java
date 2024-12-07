package com.groom.orbit.quest.app;

import org.springframework.stereotype.Service;

import com.groom.orbit.ai.app.AiService;
import com.groom.orbit.goal.app.MemberGoalService;
import com.groom.orbit.goal.dao.entity.MemberGoal;
import com.groom.orbit.quest.app.dto.response.RecommendQuestListResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestRecommendService {

  private final AiService aiService;
  private final MemberGoalService memberGoalService;

  public RecommendQuestListResponseDto recommendQuest(Long memberId, Long memberGoalId) {
    MemberGoal memberGoal = memberGoalService.findMemberGoal(memberGoalId);
    return aiService.recommendQuest(memberId, memberGoal);
  }
}
