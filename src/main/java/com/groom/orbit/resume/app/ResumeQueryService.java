package com.groom.orbit.resume.app;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.resume.app.dto.GetResumeResponseDto;
import com.groom.orbit.resume.app.dto.ResumeResponseDto;
import com.groom.orbit.resume.dao.ResumeRepository;
import com.groom.orbit.resume.dao.entity.ResumeCategory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResumeQueryService {

  private final ResumeRepository resumeRepository;

  public GetResumeResponseDto getResume(Long memberId) {

    Map<ResumeCategory, List<ResumeResponseDto>> categorizedResumes =
        resumeRepository.findAllByMemberId(memberId).stream()
            .map(ResumeResponseDto::toResumeResponseDto)
            .collect(Collectors.groupingBy(ResumeResponseDto::resumeCategory));

    return new GetResumeResponseDto(
        categorizedResumes.getOrDefault(ResumeCategory.ACADEMY, List.of()),
        categorizedResumes.getOrDefault(ResumeCategory.CAREER, List.of()),
        categorizedResumes.getOrDefault(ResumeCategory.QUALIFICATION, List.of()),
        categorizedResumes.getOrDefault(ResumeCategory.EXPERIENCE, List.of()),
        categorizedResumes.getOrDefault(ResumeCategory.ETC, List.of()));
  }
}
