package com.ssafy.trip.character.controller;

import com.ssafy.trip.character.domain.MemberCharacter;
import com.ssafy.trip.character.dto.CharacterListOfMemberResponse;
import com.ssafy.trip.character.dto.CharacterResponse;
import com.ssafy.trip.character.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/characters")
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping("/members/{memberId}")
    public ResponseEntity<CharacterListOfMemberResponse> findCharactersByMemberId(
            @PathVariable("memberId") Long memberId
    ) {
        List<MemberCharacter> characters = characterService.findCharactersByMemberId(memberId);
        List<CharacterResponse> characterDtoList = characters.stream()
                .map(character -> CharacterResponse.builder()
                        .id(character.getId())
                        .level(character.getLevel())
                        .exp(character.getExp())
                        .sidoId(character.getCharacter().getSidoId().getNo())
                        .imageUrl(character.getCharacter().getImageUrl())
                        .build()
                )
                .collect(Collectors.toList());

        CharacterListOfMemberResponse responseDto = CharacterListOfMemberResponse.builder()
                .characters(characterDtoList)
                .build();

        return ResponseEntity.ok(responseDto);
    }
}
