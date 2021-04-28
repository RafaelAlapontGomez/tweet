package com.example.tweet.dtos;

import lombok.*;

import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class UserDto implements Serializable {
    private Long userId;
    private String name;
    private String lastName;
    private Long followers;
}

