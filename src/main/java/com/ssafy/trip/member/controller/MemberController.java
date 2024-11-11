package com.ssafy.trip.member.controller;

import com.ssafy.trip.member.dto.MemberResponse;
import com.ssafy.trip.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> findById(@PathVariable("memberId") Long id) {
        return memberService.findById(id);
    }
}