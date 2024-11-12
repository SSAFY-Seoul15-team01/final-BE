package com.ssafy.trip.member.controller;

import com.ssafy.trip.member.domain.Member;
import com.ssafy.trip.member.dto.MemberResponse;
import com.ssafy.trip.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> findById(@PathVariable("memberId") Long id) {
        Member member = memberService.findById(id);
        MemberResponse memberResponse = MemberResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileUrl(member.getProfileUrl())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(memberResponse);
    }
}