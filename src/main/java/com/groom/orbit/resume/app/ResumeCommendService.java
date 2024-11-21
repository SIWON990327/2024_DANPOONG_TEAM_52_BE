package com.groom.orbit.resume.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.member.app.MemberQueryService;
import com.groom.orbit.member.dao.jpa.entity.Member;
import com.groom.orbit.resume.app.dto.CreateResumeRequestDto;
import com.groom.orbit.resume.dao.ResumeRepository;
import com.groom.orbit.resume.dao.entity.Resume;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeCommendService {

  private final ResumeRepository resumeRepository;
  private final MemberQueryService memberQueryService;

  public CommonSuccessDto createResume(Long memberId, CreateResumeRequestDto request) {

    Member member = memberQueryService.findMember(memberId);

    Resume resume =
        Resume.builder()
            .resumeCategory(request.resumeCategory())
            .content(request.content())
            .startDate(request.startDate())
            .endDate(request.endDate())
            .member(member)
            .build();

    resumeRepository.save(resume);

    return CommonSuccessDto.fromEntity(true);
  }
}
