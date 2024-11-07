package com.ssafy.trip.attraction.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "contenttypes")
public class ContentType {
    @Id
    private Integer contentTypeId;

    @NotNull
    @Column(length = 10)
    private String contentTypeName;
}
