package com.groom.orbit.job.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groom.orbit.job.dao.jpa.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {}
