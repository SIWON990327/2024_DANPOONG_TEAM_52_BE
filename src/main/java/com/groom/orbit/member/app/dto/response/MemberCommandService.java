package com.groom.orbit.member.app.dto.response;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.config.openai.AiFeedbackRequestDto;
import com.groom.orbit.config.openai.AiFeedbackResponseDto;
import com.groom.orbit.config.openai.OpenAiClient;
import com.groom.orbit.job.app.InterestJobService;
import com.groom.orbit.job.app.dto.JobDetailResponseDto;
import com.groom.orbit.member.dao.jpa.MemberRepository;
import com.groom.orbit.resume.app.ResumeQueryService;
import com.groom.orbit.resume.app.dto.GetResumeResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

  private final MemberRepository memberRepository;
  private final InterestJobService interestJobService;
  private final ResumeQueryService resumeQueryService;
  private final OpenAiClient openAiClient;

  private AiFeedbackRequestDto createAiFeedbackRequest(Long memberId) {

    List<String> jobName =
        interestJobService.findJobsByMemberId(memberId).stream()
            .map(JobDetailResponseDto::name)
            .toList();
    String job = String.join(",", jobName);

    GetResumeResponseDto getResumeResponseDto = resumeQueryService.getResume(memberId);

    List<String> categoryStringList =
        resumeQueryService.convertToResumeStrings(getResumeResponseDto);

    String academy = categoryStringList.get(0);
    String career = categoryStringList.get(1);
    String qualification = categoryStringList.get(2);
    String experience = categoryStringList.get(3);
    String etc = categoryStringList.get(4);

    return AiFeedbackRequestDto.from(job, academy, career, qualification, experience, etc);
  }

  public String createAiFeedbackResponse(Long memberId) {
    AiFeedbackResponseDto responseDto =
        openAiClient.createAiFeedback(createAiFeedbackRequest(memberId));
    return String.valueOf(responseDto.getAnswer());
  }
}
