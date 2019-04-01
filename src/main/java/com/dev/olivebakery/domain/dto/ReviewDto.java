package com.dev.olivebakery.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReviewDto {

    private String userName;
    private String content;
    private Date date;

}
