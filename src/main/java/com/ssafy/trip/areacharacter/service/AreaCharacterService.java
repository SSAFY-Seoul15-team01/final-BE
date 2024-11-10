package com.ssafy.trip.areacharacter.service;

import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.ssafy.trip.attraction.domain.Attraction;
import com.ssafy.trip.areacharacter.domain.AreaCharacter;
import com.ssafy.trip.areacharacter.domain.MemberCharacter;
import com.ssafy.trip.common.exception.NotCertifiedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AreaCharacterService {
    List<MemberCharacter> findCharactersByMemberId(Long memberId);

    AreaCharacter createCharacterOfMember(MultipartFile imageFile, Integer attractionId) throws IOException, ImageProcessingException, NotCertifiedException;

    GeoLocation getGeoLocation(MultipartFile imageFile) throws IOException, ImageProcessingException, NotCertifiedException;

    boolean isExistNear(GeoLocation geoLocation, Attraction attraction);
}
