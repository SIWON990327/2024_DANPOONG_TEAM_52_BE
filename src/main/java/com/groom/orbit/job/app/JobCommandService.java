package com.groom.orbit.job.app;

import java.util.List;

import org.springframework.stereotype.Service;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.job.app.dto.InterestJobRequestDto;
import com.groom.orbit.job.dao.jpa.entity.Job;
import com.groom.orbit.member.app.MemberQueryService;
import com.groom.orbit.member.dao.jpa.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobCommandService {

  private final MemberQueryService memberQueryService;
  private final JobQueryService jobQueryService;
  private final InterestJobService interestJobService;

  public CommonSuccessDto saveInterestJob(Long memberId, InterestJobRequestDto dto) {
    Member member = getMember(memberId);
    List<Job> jobs = jobQueryService.findJobsByIds(dto.ids());
    interestJobService.saveInterestJob(member, jobs);

    return CommonSuccessDto.fromEntity(true);
  }

  private Member getMember(Long memberId) {
    return memberQueryService.findMember(memberId);
  }
}
