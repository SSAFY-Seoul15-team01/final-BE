package com.ssafy.trip.member.service;

import com.ssafy.trip.common.exception.MemberNotFoundException;
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
    public MemberResponse findById(Long id) throws MemberNotFoundException {
        Member member = memberRepository.findById(id).orElse(null);
        if(member == null || member.getDeletedAt() != null) {
            throw new MemberNotFoundException("The user ID " + id + " is invalid or the account no longer exists.");
        }
        return MemberResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileUrl(member.getProfileUrl())
                .build();
    }

}