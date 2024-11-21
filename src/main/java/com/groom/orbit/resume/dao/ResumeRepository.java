package com.groom.orbit.resume.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groom.orbit.resume.dao.entity.Resume;
import com.groom.orbit.resume.dao.entity.ResumeCategory;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

  Optional<Resume> findById(Long resumeId);

  List<Resume> findAllByResumeCategoryAndMemberId(ResumeCategory resumeCategory, Long memberId);
}
