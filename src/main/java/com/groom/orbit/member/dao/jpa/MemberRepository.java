package com.groom.orbit.member.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groom.orbit.member.dao.jpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {}
