package com.example.tweet.mapper.impl;

import com.example.tweet.dtos.TweetDto;
import com.example.tweet.entities.TweetEntity;
import com.example.tweet.mapper.RestGenericMapper;
import com.example.tweet.mapper.RestMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class TweetRestMapper extends RestGenericMapper<TweetEntity, TweetDto>
    implements RestMapper<TweetEntity, TweetDto> {

    public TweetRestMapper(final ModelMapper modelMapper) {
        super(modelMapper);
    }
}
