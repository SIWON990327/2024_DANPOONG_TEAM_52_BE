package com.groom.orbit.member.dao.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.groom.orbit.job.dao.jpa.entity.InterestJob;
import com.groom.orbit.job.dao.jpa.entity.Job;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "member")
@Table(name = "member")
@Getter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Column(name = "nickname", length = 100)
  private String nickname;

  @ColumnDefault("''")
  @Column(name = "image_url", length = 500)
  private String imageUrl = "";

  @Column(name = "known_prompt", length = 1000)
  private String knownPrompt = "";

  @Column(name = "help_prompt", length = 1000)
  private String helpPrompt = "";

  @Column(name = "is_notification")
  private Boolean isNotification = false;

  @Column(name = "is_profile")
  private Boolean isProfile = false;

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<InterestJob> interestJobs = new ArrayList<>();

  public void addInterestJobs(List<Job> jobs) {
    List<InterestJob> interestJobs =
        jobs.stream().map(job -> InterestJob.create(this, job)).toList();
    this.interestJobs.addAll(interestJobs);
  }
}
