package com.groom.orbit.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.annotation.AuthMember;
import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.member.app.dto.response.MemberCommandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberCommandController {

  private final MemberCommandService memberCommandService;

  @PostMapping("/ai")
  public ResponseDto<String> createAiFeedback(@AuthMember Long memberId) {
    return ResponseDto.ok(memberCommandService.createAiFeedbackResponse(memberId));
  }
}
