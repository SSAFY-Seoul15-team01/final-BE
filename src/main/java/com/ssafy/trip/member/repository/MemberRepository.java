package com.ssafy.trip.member.repository;

import com.ssafy.trip.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsBySocialId(String socialId);
    Member findBySocialId(String socialId);
    Optional<Member> findByIdAndDeletedAtIsNotNull(long id);
}
