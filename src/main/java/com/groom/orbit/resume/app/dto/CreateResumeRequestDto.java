package com.groom.orbit.resume.app.dto;

import java.util.Date;

import com.groom.orbit.resume.dao.entity.ResumeCategory;

public record CreateResumeRequestDto(
    ResumeCategory resumeCategory, String content, Date startDate, Date endDate) {}
