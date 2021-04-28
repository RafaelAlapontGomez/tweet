package com.example.tweet.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class HttpMessageDto implements Serializable {
    Timestamp timestamp;
    HttpStatus status;
    String message;
    List<String> errors;
}
