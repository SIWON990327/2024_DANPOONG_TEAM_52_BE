package com.groom.orbit.member.dao.jpa;

import com.groom.orbit.member.dao.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
