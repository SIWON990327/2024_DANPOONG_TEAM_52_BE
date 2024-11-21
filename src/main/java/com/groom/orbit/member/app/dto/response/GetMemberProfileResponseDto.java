package com.groom.orbit.member.app.dto.response;

import lombok.Builder;

@Builder
public record GetMemberProfileResponseDto(String imageUrl, String nickname) {}
