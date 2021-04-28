package com.example.tweet.dtos;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class TweetDto implements Serializable {

    private Long tweetId;
    private UserDto user;
    private String idioma;
    private String texto;
    private String localizacion;
    private Boolean validation;
    private String hashtags;
}
