package com.groom.orbit.job.app;

import java.util.List;

import org.springframework.stereotype.Service;

import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.job.app.dto.InterestJobRequestDto;
import com.groom.orbit.job.dao.jpa.entity.Job;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobCommandService {

  private final JobQueryService jobQueryService;
  private final InterestJobService interestJobService;

  public CommonSuccessDto saveInterestJob(Long memberId, InterestJobRequestDto dto) {
    List<Job> jobs = jobQueryService.findJobsByIds(dto.ids());
    interestJobService.saveInterestJob(memberId, jobs);

    return CommonSuccessDto.fromEntity(true);
  }
}
