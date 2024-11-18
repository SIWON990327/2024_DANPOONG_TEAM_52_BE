package com.groom.orbit.auth.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groom.orbit.auth.dao.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByNickname(String email);
}
