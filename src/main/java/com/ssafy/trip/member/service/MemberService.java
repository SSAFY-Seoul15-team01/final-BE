package com.ssafy.trip.member.service;

import com.ssafy.trip.member.domain.Member;
import com.ssafy.trip.member.dto.MemberResponse;
import com.ssafy.trip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<MemberResponse> findById(Long id) {
        Member member = memberRepository.findById(id).orElse(null);
        if(member == null) {
            return ResponseEntity.notFound().build();
        }
        MemberResponse memberResponse = MemberResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileUrl(member.getProfileUrl())
                .build();
        return ResponseEntity.ok(memberResponse);
    }

}