package com.ssafy.trip.areacharacter.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.attraction.repository.AttractionRepository;
import com.ssafy.trip.areacharacter.domain.AreaCharacter;
import com.ssafy.trip.areacharacter.domain.MemberCharacter;
import com.ssafy.trip.areacharacter.repository.AreaCharacterRepository;
import com.ssafy.trip.areacharacter.repository.MemberCharacterRepository;
import com.ssafy.trip.common.exception.custom.MemberNotFoundException;
import com.ssafy.trip.common.exception.custom.NotCertifiedException;
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
public class AreaCharacterServiceImpl implements AreaCharacterService {
    private final MemberCharacterRepository memberCharacterRepository;
    private final AttractionRepository attractionRepository;
    private final MemberRepository memberRepository;
    private final AreaCharacterRepository areaCharacterRepository;

    // 20 미터 오차 범위
    private static final BigDecimal LATITUDE_ERROR_RANGE = BigDecimal.valueOf(0.000179);
    private static final BigDecimal LONGITUDE_ERROR_RANGE = BigDecimal.valueOf(0.000227);
    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer DEFAULT_EXP = 0;

    @Override
    public List<MemberCharacter> findCharactersByMemberId(Long memberId) {
        return memberCharacterRepository.findByMemberId(memberId);
    }

    @Override
    public AreaCharacter createCharacterOfMember(MultipartFile imageFile, Integer attractionId, Long memberId)
            throws IOException, ImageProcessingException, NotCertifiedException {
        GeoLocation geoLocation = getGeoLocation(imageFile);
        Attraction attraction = attractionRepository.findByNo(attractionId);

        if (!isExistNear(geoLocation, attraction)) {
            throw new NotCertifiedException("User Location is not near the attraction");
        }

        Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId).orElseThrow(() ->
                new MemberNotFoundException("The user ID " + memberId + " is invalid or the account no longer exists."));
        AreaCharacter areaCharacter = areaCharacterRepository.findBySidoId(attraction.getAreaCode());

        MemberCharacter memberCharacter = memberCharacterRepository.save(MemberCharacter.builder()
                .level(DEFAULT_LEVEL)
                .exp(DEFAULT_EXP)
                .member(member)
                .areaCharacter(areaCharacter)
                .build());

        return memberCharacter.getAreaCharacter();
    }

    @Override
    public GeoLocation getGeoLocation(MultipartFile imageFile) throws IOException, ImageProcessingException, NotCertifiedException {
        Metadata metadata = ImageMetadataReader.readMetadata(imageFile.getInputStream());
        GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);

        if (gpsDirectory == null || gpsDirectory.getGeoLocation() == null) {
            throw new NotCertifiedException("Geo location is null");
        }

        return gpsDirectory.getGeoLocation();
    }

    @Override
    public boolean isExistNear(GeoLocation geoLocation, Attraction attraction) {
        BigDecimal userLatitude = BigDecimal.valueOf(geoLocation.getLatitude());
        BigDecimal userLongitude = BigDecimal.valueOf(geoLocation.getLongitude());
        BigDecimal attractionLatitude = attraction.getLatitude();
        BigDecimal attractionLongitude = attraction.getLongitude();

        return userLatitude.compareTo(attractionLatitude.subtract(LATITUDE_ERROR_RANGE)) == 1
                && userLatitude.compareTo(attractionLatitude.add(LATITUDE_ERROR_RANGE)) == -1
                && userLongitude.compareTo(attractionLongitude.subtract(LONGITUDE_ERROR_RANGE)) == 1
                && userLongitude.compareTo(attractionLongitude.add(LONGITUDE_ERROR_RANGE)) == -1;
    }
}
