package com.groom.orbit.resume.app.dto;

import java.time.LocalDate;

import com.groom.orbit.resume.dao.entity.Resume;
import com.groom.orbit.resume.dao.entity.ResumeCategory;

import lombok.Builder;

@Builder
public record ResumeResponseDto(
    ResumeCategory resumeCategory,
    String title,
    String content,
    LocalDate startDate,
    LocalDate endDate) {

  public static ResumeResponseDto toResumeResponseDto(Resume resume) {
    return ResumeResponseDto.builder()
        .resumeCategory(resume.getResumeCategory())
        .title(resume.getTitle())
        .content(resume.getContent())
        .startDate(resume.getStartDate())
        .endDate(resume.getEndDate())
        .build();
  }
}
