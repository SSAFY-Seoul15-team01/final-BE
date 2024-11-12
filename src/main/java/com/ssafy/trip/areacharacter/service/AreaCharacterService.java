package com.ssafy.trip.areacharacter.service;

import com.drew.lang.GeoLocation;
import com.ssafy.trip.areacharacter.domain.AreaCharacter;
import com.ssafy.trip.areacharacter.domain.MemberCharacter;
import com.ssafy.trip.attraction.domain.Attraction;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AreaCharacterService {
    List<MemberCharacter> findCharactersByMemberId(Long memberId);

    AreaCharacter createCharacterOfMember(MultipartFile imageFile, Integer attractionId, Long id);

    GeoLocation getGeoLocation(MultipartFile imageFile);

    boolean isExistNear(GeoLocation geoLocation, Attraction attraction);
}
