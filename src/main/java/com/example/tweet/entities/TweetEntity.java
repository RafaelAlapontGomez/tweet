package com.example.tweet.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor @Builder @ToString
@Entity
@Table(name = "tweet")
public class TweetEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tweetId;

    private String idioma;
    private String texto;
    private String localizacion;
    private Boolean validation;
    private String hashtags;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
}
