package com.ssafy.trip.areacharacter.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@ToString
public class CreateCharacterOfMemberRequest {
    @NotNull
    private MultipartFile imageFile;
}
