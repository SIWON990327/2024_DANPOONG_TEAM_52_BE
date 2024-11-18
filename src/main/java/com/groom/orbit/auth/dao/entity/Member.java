package com.groom.orbit.auth.dao.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Column(name = "nickname", length = 100)
  private String nickname;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "known_prompt", length = 1000)
  private String knownPrompt;

  @Column(name = "help_prompt", length = 1000)
  private String helpPrompt;

  @Column(name = "is_notification")
  private Boolean isNotification;

  @Column(name = "is_profile")
  private Boolean isProfile;
}
