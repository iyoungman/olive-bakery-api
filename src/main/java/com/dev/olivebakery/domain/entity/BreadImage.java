package com.dev.olivebakery.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class BreadImage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long breadImageId;

    @NotNull
    private String imageName;

    @NotNull
    private String imageType;

    @NotNull
    private Long imageSize;

    @NotNull
    private String imageUrl;
}
