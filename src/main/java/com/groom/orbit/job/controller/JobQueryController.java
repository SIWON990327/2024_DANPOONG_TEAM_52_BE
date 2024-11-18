package com.groom.orbit.job.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.job.app.JobQueryService;
import com.groom.orbit.job.app.dto.JobDetailResponseDto;
import com.groom.orbit.job.app.dto.JobGroupingByCategoryResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class JobQueryController {

  private final JobQueryService jobQueryService;

  @GetMapping
  public ResponseDto<JobGroupingByCategoryResponseDto> findJobs() {
    return ResponseDto.ok(jobQueryService.findAllJobs());
  }

  /** TODO add annotation */
  @GetMapping("/member")
  public ResponseDto<List<JobDetailResponseDto>> findJobs(@AuthMember Long memberId) {
    return ResponseDto.ok(jobQueryService.findJobsByUser(memberId));
  }
}
