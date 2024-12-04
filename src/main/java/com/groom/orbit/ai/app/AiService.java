package com.groom.orbit.ai.app;

import com.groom.orbit.goal.app.dto.request.CreateGoalRequestDto;
import com.groom.orbit.goal.app.dto.response.RecommendQuestResponseDto;
import com.groom.orbit.member.app.dto.response.GetFeedbackResponseDto;
import com.groom.orbit.resume.app.dto.GetResumeResponseDto;

public interface AiService {

  GetFeedbackResponseDto getMemberFeedback(String interestJobs, GetResumeResponseDto dto);

  CreateGoalRequestDto recommendGoal(Long memberId);

  RecommendQuestResponseDto recommendQuest(Long memberId);
}