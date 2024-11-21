package com.groom.orbit.resume.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.resume.app.ResumeCommendService;
import com.groom.orbit.resume.app.dto.CreateResumeRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class ResumeCommandController {

  private final ResumeCommendService resumeCommandService;

  @PostMapping()
  public ResponseDto<CommonSuccessDto> createResume(
      @AuthMember Long memberId, @RequestBody CreateResumeRequestDto requestDto) {
    return ResponseDto.ok(resumeCommandService.createResume(memberId, requestDto));
  }
}
