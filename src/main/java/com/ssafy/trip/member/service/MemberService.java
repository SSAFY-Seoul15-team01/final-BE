package com.ssafy.trip.member.service;

import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.common.exception.custom.NotFoundException;
import com.ssafy.trip.member.domain.Member;
import com.ssafy.trip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() ->
                new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

}