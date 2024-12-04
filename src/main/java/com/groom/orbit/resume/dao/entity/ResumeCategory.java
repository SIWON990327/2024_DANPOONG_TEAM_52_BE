package com.groom.orbit.resume.dao.entity;

import lombok.Getter;

@Getter
public enum ResumeCategory {
  ACADEMY("학력"),
  CAREER("경력"),
  QUALIFICATION("자격, 어학, 수상"),
  EXPERIENCE("경험, 활동, 교육"),
  ETC("기타사항");

  private final String category;

  ResumeCategory(String category) {
    this.category = category;
  }
}
