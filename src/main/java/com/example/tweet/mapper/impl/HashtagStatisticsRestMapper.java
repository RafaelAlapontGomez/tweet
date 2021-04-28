package com.example.tweet.mapper.impl;

import com.example.tweet.dtos.HashtagStatisticsDto;
import com.example.tweet.dtos.TweetDto;
import com.example.tweet.entities.TweetEntity;
import com.example.tweet.entities.aggregations.HashtagsAnswerStatistics;
import com.example.tweet.mapper.RestGenericMapper;
import com.example.tweet.mapper.RestMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class HashtagStatisticsRestMapper extends RestGenericMapper<HashtagsAnswerStatistics, HashtagStatisticsDto>
    implements RestMapper<HashtagsAnswerStatistics, HashtagStatisticsDto> {

    public HashtagStatisticsRestMapper(final ModelMapper modelMapper) {
        super(modelMapper);
    }
}
