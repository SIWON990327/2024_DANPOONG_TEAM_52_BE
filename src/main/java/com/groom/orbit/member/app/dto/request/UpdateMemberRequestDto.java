package com.groom.orbit.member.app.dto.request;

public record UpdateMemberRequestDto(
    String nickname,
    String knownPrompt,
    String helpPrompt,
    Boolean isNotification,
    Boolean isProfile) {}
