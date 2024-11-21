package com.groom.orbit.resume.app;

import java.util.Arrays;
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

    Map<ResumeCategory, List<ResumeResponseDto>> resumeMap =
        Arrays.stream(ResumeCategory.values())
            .collect(
                Collectors.toMap(
                    category -> category, category -> findResumesByCategory(memberId, category)));

    return new GetResumeResponseDto(
        resumeMap.get(ResumeCategory.ACADEMY),
        resumeMap.get(ResumeCategory.CAREER),
        resumeMap.get(ResumeCategory.QUALIFICATION),
        resumeMap.get(ResumeCategory.EXPERIENCE),
        resumeMap.get(ResumeCategory.ETC));
  }

  private List<ResumeResponseDto> findResumesByCategory(Long memberId, ResumeCategory category) {
    return resumeRepository.findAllByResumeCategoryAndMemberId(category, memberId).stream()
        .map(ResumeResponseDto::toResumeResponseDto)
        .toList();
  }
}
