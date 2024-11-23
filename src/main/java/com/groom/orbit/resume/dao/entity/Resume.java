package com.groom.orbit.resume.dao.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.groom.orbit.member.dao.jpa.entity.Member;
import com.groom.orbit.resume.app.dto.ResumeRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "resume")
public class Resume {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "resume_id")
  private Long resumeId;

  @Column(name = "category", length = 10)
  @Enumerated(EnumType.STRING)
  private ResumeCategory resumeCategory;

  @Column(name = "title", length = 50)
  private String title;

  @Column(name = "content", length = 50)
  private String content;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  public void updateResume(ResumeRequestDto requestDto) {
    this.resumeCategory = requestDto.resumeCategory();
    this.title = requestDto.title();
    this.content = requestDto.content();
    this.startDate = requestDto.startDate();
    this.endDate = requestDto.endDate();
  }
}
