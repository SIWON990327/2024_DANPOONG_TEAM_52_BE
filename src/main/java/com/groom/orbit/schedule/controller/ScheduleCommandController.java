package com.groom.orbit.schedule.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.CommonSuccessDto;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.schedule.app.ScheduleCommandService;
import com.groom.orbit.schedule.app.dto.ScheduleRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleCommandController {

  private final ScheduleCommandService scheduleCommandService;

  @PostMapping
  public ResponseDto<CommonSuccessDto> createSchedule(
      @AuthMember Long member, @RequestBody ScheduleRequestDto scheduleRequestDto) {
    return ResponseDto.ok(scheduleCommandService.createSchedule(member, scheduleRequestDto));
  }
}
