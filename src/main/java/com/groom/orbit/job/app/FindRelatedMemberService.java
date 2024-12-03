package com.groom.orbit.job.app;

import org.springframework.stereotype.Service;

import com.groom.orbit.job.app.dto.GetRelatedInterestJob;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindRelatedMemberService {

  private final InterestJobService interestJobService;

  public GetRelatedInterestJob findRelatedMembersInterestJob(Long memberId) {
    return null;
  }
}
