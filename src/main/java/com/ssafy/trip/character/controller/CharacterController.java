package com.ssafy.trip.character.controller;

import com.drew.imaging.ImageProcessingException;
import com.ssafy.trip.character.domain.Character;
import com.ssafy.trip.character.domain.MemberCharacter;
import com.ssafy.trip.character.dto.CharacterListOfMemberResponse;
import com.ssafy.trip.character.dto.CharacterResponse;
import com.ssafy.trip.character.dto.CreatedCharacterResponse;
import com.ssafy.trip.character.service.CharacterService;
import com.ssafy.trip.common.exception.NotCertifiedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping("/attractions/{attractionId}")
    public ResponseEntity<?> createCharacterOfMember(
            @PathVariable Integer attractionId, @RequestPart MultipartFile imageFile
    ) {
        try {
            Character character = characterService.createCharacterOfMember(imageFile, attractionId);
            CreatedCharacterResponse characterResponse = CreatedCharacterResponse.builder()
                    .character(character)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(characterResponse);
        } catch (ImageProcessingException e) {
            return ResponseEntity.badRequest().body("Failed to process image metadata: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to read image file: " + e.getMessage());
        } catch (NotCertifiedException e) {
            return ResponseEntity.badRequest().body("Failed to certify travel: " + e.getMessage());
        }
    }

}
