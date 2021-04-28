package com.example.tweet.service.impl;

import com.example.tweet.controllers.exceptions.TweetNoPersistidoException;
import com.example.tweet.controllers.exceptions.TweetNoPresenteException;
import com.example.tweet.controllers.exceptions.UserNoPresenteException;
import com.example.tweet.entities.aggregations.HashtagsAnswerStatistics;
import com.example.tweet.entities.TweetEntity;
import com.example.tweet.entities.UserEntity;
import com.example.tweet.repositories.TweetRepository;
import com.example.tweet.repositories.UserRepository;
import com.example.tweet.service.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.zip.DataFormatException;

@Slf4j
@Service
public class TweetServiceImpl implements TweetService {

    @Value("${application.properties.umbral.followers}")
    private Long umbralFollowers;

    @Value("${application.properties.idiomas.permitidos}")
    private List<String> idiomasPermitidos;

    Predicate<UserEntity> userPresent = x -> Optional.ofNullable(x).isPresent();
    Predicate<TweetEntity> followersPermiso = x -> x.getUser().getFollowers() >= umbralFollowers;
    Predicate<TweetEntity> idiomaPermitido = x -> idiomasPermitidos.contains(x.getIdioma());

    private TweetRepository repository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    TweetServiceImpl(TweetRepository repository, UserRepository userRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TweetEntity create(TweetEntity tweetEntityCreate) {
        UserEntity user = searchUser(tweetEntityCreate.getUser().getUserId());
        tweetEntityCreate.setUser(user);

        TweetEntity newTweet = null;
        if( followersPermiso.and(idiomaPermitido).test(tweetEntityCreate) ) {
            newTweet = repository.save(tweetEntityCreate);
        } else {
            throw new TweetNoPersistidoException();
        }

        return newTweet;
    }

    @Override
    public Page<TweetEntity> findAll(Pageable pageable) {
        log.info("Run ==> {}", "findAll");
        return repository.findAll(pageable);
    }

    @Override
    public Optional<TweetEntity> findByPrimaryKey(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<TweetEntity> markLikeValidate(Long id) {
        TweetEntity tweet = repository.getOne(id);
        if (!Optional.ofNullable(tweet).isPresent()) {
            throw new TweetNoPresenteException();
        }
        tweet.setValidation(true);
        return Optional.ofNullable(repository.save(tweet));
    }

    @Override
    public List<HashtagsAnswerStatistics> findHashtagsAnswerStatistics() {
        return repository.findHashtagsAnswerStatistics();
    }

    @Override
    public Page<TweetEntity> findByUser(Long userId, Pageable pageable) {
        UserEntity user = searchUser(userId);

        return repository.findByUserAndValidation(user, Boolean.TRUE, pageable);
    }

    private UserEntity searchUser(Long userId) {
        UserEntity user = userRepository.getOne(userId);
        if (userPresent.test(user)) {
            return user;
        } else {
            throw new UserNoPresenteException();
        }
    }

}
