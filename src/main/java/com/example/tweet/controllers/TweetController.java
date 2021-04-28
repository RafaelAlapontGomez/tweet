package com.example.tweet.controllers;

import com.example.tweet.controllers.exceptions.DataNotFoundException;
import com.example.tweet.dtos.HashtagStatisticsDto;
import com.example.tweet.dtos.TweetDto;
import com.example.tweet.entities.TweetEntity;
import com.example.tweet.entities.aggregations.HashtagsAnswerStatistics;
import com.example.tweet.mapper.RestMapper;
import com.example.tweet.service.TweetService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tweet")
public class TweetController {

    @Value("${application.properties.hastags.top}")
    private Long topHastags;

    private TweetService service;
    private RestMapper<TweetEntity, TweetDto> mapper;
    private RestMapper<HashtagsAnswerStatistics, HashtagStatisticsDto> mapperStatis;

    TweetController(TweetService service, RestMapper<TweetEntity, TweetDto> mapper,
                    RestMapper<HashtagsAnswerStatistics, HashtagStatisticsDto> mapperStatis) {
        this.service = service;
        this.mapper = mapper;
        this.mapperStatis = mapperStatis;
    }

    Function<Page<TweetEntity>, Page<TweetDto>> toDtoPage = entities -> entities.map(mapper::toDTO);

    @PostMapping
    public ResponseEntity<TweetDto> create(@RequestBody TweetDto tweetDto) {

        TweetDto created = Optional.ofNullable(tweetDto)
                .map(mapper::toEntity)
                .map(service::create)
                .map(mapper::toDTO)
                .get();

        URI location = URI.create(String.format("/tweet/%s", created.getTweetId()));
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<TweetDto>> getAll(
            @RequestParam(defaultValue = "0", value = "page", required = true) int page,
            @RequestParam(defaultValue = "10", value = "size", required = true) int size
    ) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<TweetDto> dtoList = Optional.ofNullable(service.findAll(pageRequest))
                .map(toDtoPage)
                .orElse(Page.empty());

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("{id}")
    public ResponseEntity<TweetDto> findByPrimaryKey( @PathVariable("id") Long id ) {
        return service
                .findByPrimaryKey(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(DataNotFoundException::new);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TweetDto> markLikeRevised( @PathVariable("id") Long id ) {
        return service
                .markLikeValidate(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(DataNotFoundException::new);
    }

    @GetMapping("hashtag-statistics")
    public ResponseEntity<List<HashtagStatisticsDto>> findHashtagsAnswerStatistics() {
        List<HashtagsAnswerStatistics> statistics = service.findHashtagsAnswerStatistics();
        List<HashtagStatisticsDto> statisticsDtos =
                statistics.stream()
                .sorted(Comparator.comparingLong(HashtagsAnswerStatistics::getAparece).reversed())
                .limit(topHastags)
                .map(mapperStatis::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(statisticsDtos);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<Page<TweetDto>> findByUser(@PathVariable("userId") Long userId,
                         @RequestParam(defaultValue = "0", value = "page", required = true) int page,
                         @RequestParam(defaultValue = "10", value = "size", required = true) int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<TweetDto> dtoList = Optional.ofNullable(service.findByUser(userId, pageRequest))
                .map(toDtoPage)
                .orElse(Page.empty());

        return ResponseEntity.ok(dtoList);
    }
}
