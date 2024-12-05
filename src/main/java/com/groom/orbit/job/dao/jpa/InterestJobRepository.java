package com.groom.orbit.job.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.groom.orbit.job.dao.jpa.entity.InterestJob;
import com.groom.orbit.job.dao.jpa.entity.InterestJobId;

public interface InterestJobRepository extends JpaRepository<InterestJob, InterestJobId> {

  List<InterestJob> findAllByMemberId(@Param("memberId") Long memberId);

  @Query(
      "select ij.memberId from InterestJob ij"
          + " join ij.member m"
          + " where ij.jobId in :interest_job_names")
  List<Long> findByJobNames(@Param("interest_job_names") List<Long> interestJobNames);
}
