package com.example.tweet.service;

import com.example.tweet.entities.aggregations.HashtagsAnswerStatistics;
import com.example.tweet.entities.TweetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

public interface TweetService {
    TweetEntity create(TweetEntity tweetEntityCreate);
    Page<TweetEntity> findAll(Pageable pageable);
    Optional<TweetEntity> findByPrimaryKey(Long id);
    Optional<TweetEntity> markLikeValidate(Long id);
    List<HashtagsAnswerStatistics> findHashtagsAnswerStatistics();
    Page<TweetEntity> findByUser(Long userId, Pageable pageable);

}
