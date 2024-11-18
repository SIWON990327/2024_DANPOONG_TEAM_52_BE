package com.groom.orbit.job.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.job.app.JobQueryService;
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
}
