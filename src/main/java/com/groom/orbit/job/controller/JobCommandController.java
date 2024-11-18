package com.groom.orbit.job.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.job.app.JobCommandService;
import com.groom.orbit.job.app.dto.InterestJobRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/job/member")
@RequiredArgsConstructor
public class JobCommandController {

  private final JobCommandService jobCommandService;

  /** TODO add annotation */
  @PutMapping
  public ResponseDto<CommonSuccessDto> addInterestJob(
      @AuthMember Long memberId, @RequestBody InterestJobRequestDto dto) {
    return ResponseDto.created(jobCommandService.saveInterestJob(memberId, dto));
  }
}
