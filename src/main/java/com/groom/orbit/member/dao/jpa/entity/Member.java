package com.groom.orbit.member.dao.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "member")
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Column(name = "nickname", length = 100)
  private String nickname;

  @Column(name = "image_url")
  private String imageUrl = "";

  @Column(name = "known_prompt", length = 1000)
  private String knownPrompt = "";

  @Column(name = "help_prompt", length = 1000)
  private String helpPrompt = "";

  @Column(name = "is_notification")
  private Boolean isNotification = false;

  @Column(name = "is_profile")
  private Boolean isProfile = false;
}
