package com.ssafy.trip.character.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.attraction.repository.AttractionRepository;
import com.ssafy.trip.character.domain.Character;
import com.ssafy.trip.character.domain.MemberCharacter;
import com.ssafy.trip.character.repository.CharacterRepository;
import com.ssafy.trip.character.repository.MemberCharacterRepository;
import com.ssafy.trip.member.domain.Member;
import com.ssafy.trip.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final MemberCharacterRepository memberCharacterRepository;
    private final AttractionRepository attractionRepository;
    private final MemberRepository memberRepository;
    private final CharacterRepository characterRepository;

    // 20 미터 오차 범위
    private static final BigDecimal LATITUDE_ERROR_RANGE = BigDecimal.valueOf(0.000179);
    private static final BigDecimal LONGITUDE_ERROR_RANGE = BigDecimal.valueOf(0.000227);

    @Override
    public List<MemberCharacter> findCharactersByMemberId(Long memberId) {
        return memberCharacterRepository.findByMemberId(memberId);
    }

    @Override
    public Character createCharacterOfMember(MultipartFile imageFile, Integer attractionId)
            throws IOException, ImageProcessingException {
        GeoLocation geoLocation = getGeoLocation(imageFile);
        Attraction attraction = attractionRepository.findByNo(attractionId);

        // TODO: exception custom
        if (!isExistNear(geoLocation, attraction)) {
            throw new ImageProcessingException("User Location is not near the attraction");
        }

        // TODO: 소셜로그인 기능 구현되면 사용자 아이디 수정
        Member member = memberRepository.findById(1);
        Character character = characterRepository.findBySidoId(attraction.getAreaCode());

        // TODO: magic number 상수 관리
        MemberCharacter memberCharacter = memberCharacterRepository.save(MemberCharacter.builder()
                .level(1)
                .exp(0)
                .member(member)
                .character(character)
                .build());

        return memberCharacter.getCharacter();
    }

    @Override
    public GeoLocation getGeoLocation(MultipartFile imageFile) throws IOException, ImageProcessingException {
        Metadata metadata = ImageMetadataReader.readMetadata(imageFile.getInputStream());
        GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);

        if (gpsDirectory == null || gpsDirectory.getGeoLocation() == null) {
            throw new ImageProcessingException("Geo location is null");
        }

        return gpsDirectory.getGeoLocation();
    }

    @Override
    public boolean isExistNear(GeoLocation geoLocation, Attraction attraction) {
        BigDecimal userLatitude = BigDecimal.valueOf(geoLocation.getLatitude());
        BigDecimal userLongitude = BigDecimal.valueOf(geoLocation.getLongitude());
        BigDecimal attractionLatitude = attraction.getLatitude();
        BigDecimal attractionLongitude = attraction.getLongitude();

        // TODO: magic number 상수 관리
        return userLatitude.compareTo(attractionLatitude.subtract(LATITUDE_ERROR_RANGE)) == 1
                && userLatitude.compareTo(attractionLatitude.add(LATITUDE_ERROR_RANGE)) == -1
                && userLongitude.compareTo(attractionLongitude.subtract(LONGITUDE_ERROR_RANGE)) == 1
                && userLongitude.compareTo(attractionLongitude.add(LONGITUDE_ERROR_RANGE)) == -1;
    }
}
