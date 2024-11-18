package com.ssafy.trip.member.controller;

import com.ssafy.trip.member.domain.Member;
import com.ssafy.trip.member.dto.MemberResponse;
import com.ssafy.trip.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberResponse> findById(HttpSession httpSession) {
        Map<String, Object> userInfo = (HashMap<String, Object>) httpSession.getAttribute("userInfo");
        Long id = (Long) userInfo.get("id");
        Member member = memberService.findById(id);
        MemberResponse memberResponse = MemberResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileUrl(member.getProfileUrl())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(memberResponse);
    }

    @PatchMapping
    public ResponseEntity<Void> modifyMember(
            @RequestPart(required = false) MultipartFile imageFile,
            @RequestPart(required = false) String nickname,
            HttpSession httpSession) {
        Map<String, Object> userInfo = (HashMap<String, Object>) httpSession.getAttribute("userInfo");
        Long id = (Long) userInfo.get("id");
        memberService.modifyMember(id, imageFile, nickname);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}