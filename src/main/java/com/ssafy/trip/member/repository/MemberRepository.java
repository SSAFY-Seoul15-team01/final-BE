package com.ssafy.trip.member.repository;

import com.ssafy.trip.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findById(long id);
}
