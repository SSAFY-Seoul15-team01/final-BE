package com.ssafy.trip.areacharacter.controller;

import com.drew.imaging.ImageProcessingException;
import com.ssafy.trip.areacharacter.domain.AreaCharacter;
import com.ssafy.trip.areacharacter.domain.MemberCharacter;
import com.ssafy.trip.areacharacter.dto.AreaCharacterListOfMemberResponse;
import com.ssafy.trip.areacharacter.dto.AreaCharacterResponse;
import com.ssafy.trip.areacharacter.dto.CreatedCharacterResponse;
import com.ssafy.trip.areacharacter.service.AreaCharacterService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/area-characters")
public class AreaCharacterController {
    private final AreaCharacterService areaCharacterService;

    @GetMapping("/members/{memberId}")
    public ResponseEntity<AreaCharacterListOfMemberResponse> findCharactersByMemberId(
            @PathVariable("memberId") Long memberId
    ) {
        List<MemberCharacter> areaCharacters = areaCharacterService.findCharactersByMemberId(memberId);

        List<AreaCharacterResponse> areaCharacterDtoList = areaCharacters.stream()
                .map(areaCharacter -> AreaCharacterResponse.builder()
                        .id(areaCharacter.getId())
                        .level(areaCharacter.getLevel())
                        .exp(areaCharacter.getExp())
                        .sidoId(areaCharacter.getAreaCharacter().getSidoId().getNo())
                        .imageUrl(areaCharacter.getAreaCharacter().getImageUrl())
                        .build()
                )
                .collect(Collectors.toList());

        AreaCharacterListOfMemberResponse responseDto = AreaCharacterListOfMemberResponse.builder()
                .areaCharacters(areaCharacterDtoList)
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/attractions/{attractionId}")
    public ResponseEntity<?> createCharacterOfMember(
            @PathVariable Integer attractionId, @RequestPart MultipartFile imageFile, HttpSession session
    ) {
        Map<String, Object> userInfo = (HashMap<String, Object>) session.getAttribute("userInfo");
        Long memberId = (Long) userInfo.get("id");
        AreaCharacter areaCharacter = areaCharacterService.createCharacterOfMember(imageFile, attractionId, memberId);
        CreatedCharacterResponse characterResponse = CreatedCharacterResponse.builder()
                .areaCharacter(areaCharacter)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(characterResponse);
    }

}
