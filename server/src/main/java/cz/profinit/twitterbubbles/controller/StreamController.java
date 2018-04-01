package cz.profinit.twitterbubbles.controller;

import cz.profinit.twitterbubbles.model.TopWords;
import cz.profinit.twitterbubbles.streaming.TopWordsStreamFactory;
import cz.profinit.twitterbubbles.streaming.TweetStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class StreamController {

    private final TweetStream tweetStream;
    private final TopWordsStreamFactory topWordsStreamFactory;

    @GetMapping("/tweets")
    public Flux<Tweet> streamTweets() {
        log.info("Streaming tweets");
        return tweetStream.getTweets();
    }

    @GetMapping("/top-words")
    public Flux<TopWords> streamTopWords() {
        log.info("Streaming top words");
        return topWordsStreamFactory.createTopWordsStream();
    }

    @ExceptionHandler(IOException.class)
    public void handleException(IOException e) {
        log.warn("IOException occurred: {}", e.getMessage());
    }
}
