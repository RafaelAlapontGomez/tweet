package com.example.tweet.entities.aggregations;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class HashtagsAnswerStatistics {
    private String hashtags;
    private Long   aparece;
}
