package com.groom.orbit.resume.dao.entity;

import java.util.Date;

import jakarta.persistence.*;

import com.groom.orbit.member.dao.jpa.entity.Member;

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

  @Column(name = "category")
  @Enumerated(EnumType.STRING)
  private ResumeCategory resumeCategory;

  @Column(name = "content")
  private String content;

  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "end_date")
  private Date endDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;
}
