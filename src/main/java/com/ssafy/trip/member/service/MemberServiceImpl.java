package com.ssafy.trip.member.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.trip.common.exception.ErrorCode;
import com.ssafy.trip.common.exception.custom.BadRequestException;
import com.ssafy.trip.common.exception.custom.NicknameConflictException;
import com.ssafy.trip.common.exception.custom.NotFoundException;
import com.ssafy.trip.member.domain.Member;
import com.ssafy.trip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final AmazonS3 s3Client;

    @Value("${aws-adk.bucketName}")
    private String bucketName;

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() ->
                new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Override
    public void modifyMember(Long id, MultipartFile imageFile, String nickname) {
        Member member = memberRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() ->
                new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        if (nickname != null && !member.getNickname().equals(nickname)) {
            if (memberRepository.existsByNickname(nickname)) {
                throw new NicknameConflictException(ErrorCode.NICKNAME_ALREADY_EXIST);
            }
            member.updateNickname(nickname);
        }

        if (imageFile != null) {
            nickname = member.getNickname();
            member.updateProfileUrl(uploadImage(nickname, imageFile));
        }
    }

    private String uploadImage(String nickname, MultipartFile imageFile) {
        File uploadedFile = convert(imageFile);
        return uploadImage(nickname, uploadedFile);
    }

    private String uploadImage(String nickname, File imageFile) {
        String fileName = nickname + "/" + imageFile.getName();

        s3Client.putObject(
                new PutObjectRequest(bucketName, fileName, imageFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        imageFile.delete();

        return s3Client.getUrl(bucketName, fileName).toString();
    }

    private File convert(MultipartFile imageFile) {
        String filename = UUID.randomUUID().toString().substring(0, 10)
                + "_" + imageFile.getOriginalFilename();
        File convertFile = new File(filename);

        try {
            if (!convertFile.exists()) {
                convertFile.createNewFile();
            }

            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(imageFile.getBytes());
            }
        } catch (IOException e) {
            throw new BadRequestException(ErrorCode.IMAGE_READ_ERROR);
        }
        return convertFile;
    }

}