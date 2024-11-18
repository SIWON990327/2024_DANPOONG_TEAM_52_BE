package com.groom.orbit.job.dao.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

@Entity
@Getter
@Table(name = "job")
public class Job {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "job_id")
  private Long jobId;

  @Column(nullable = false, length = 50)
  private String category;

  @Column(nullable = false, length = 50)
  private String name;
}
