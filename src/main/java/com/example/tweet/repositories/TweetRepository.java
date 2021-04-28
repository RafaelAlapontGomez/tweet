package com.example.tweet.repositories;

import com.example.tweet.entities.UserEntity;
import com.example.tweet.entities.aggregations.HashtagsAnswerStatistics;
import com.example.tweet.entities.TweetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetRepository extends JpaRepository<TweetEntity, Long> {

    Page<TweetEntity> findByUserAndValidation(UserEntity user, Boolean validation, Pageable pageable);

    @Query(value="SELECT new com.example.tweet.entities.aggregations.HashtagsAnswerStatistics(c.hashtags, count(c.hashtags)) " +
            "FROM TweetEntity AS c GROUP BY c.hashtags")
    List<HashtagsAnswerStatistics> findHashtagsAnswerStatistics();
}
