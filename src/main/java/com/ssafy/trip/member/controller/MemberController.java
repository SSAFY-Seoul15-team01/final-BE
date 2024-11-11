package com.ssafy.trip.member.controller;

import com.ssafy.trip.common.exception.MemberNotFoundException;
import com.ssafy.trip.member.dto.MemberResponse;
import com.ssafy.trip.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public ResponseEntity<Object> findById(@PathVariable("memberId") Long id) {
        try {
            MemberResponse memberResponse = memberService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(memberResponse);
        } catch (MemberNotFoundException e) {
            return ResponseEntity.badRequest().body("No such user: " + e.getMessage());
        }
    }
}