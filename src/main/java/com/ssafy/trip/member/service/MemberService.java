package com.ssafy.trip.member.service;

import com.ssafy.trip.member.domain.Member;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    Member findById(Long id);
    void modifyMember(Long memberId, MultipartFile imageFile, String nickname);
}
