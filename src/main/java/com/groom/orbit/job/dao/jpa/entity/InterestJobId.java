package com.groom.orbit.job.dao.jpa.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class InterestJobId implements Serializable {

  private Long job;
  private Long member;
}
