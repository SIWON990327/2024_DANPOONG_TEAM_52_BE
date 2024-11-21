package com.groom.orbit.resume.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groom.orbit.resume.dao.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {}
