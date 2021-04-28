package com.example.tweet.dtos;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class HashtagStatisticsDto {
    private String hashtags;
    private Long   aparece;
}
