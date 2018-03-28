package cz.profinit.twitterbubbles.controller;

import cz.profinit.twitterbubbles.model.TopWords;
import cz.profinit.twitterbubbles.model.TweetStats;
import cz.profinit.twitterbubbles.streaming.TopWordsStream;
import cz.profinit.twitterbubbles.streaming.TweetStatsStream;
import cz.profinit.twitterbubbles.streaming.TweetStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StreamController {

    private final TweetStream tweetStream;
    private final TweetStatsStream tweetStatsStream;
    private final TopWordsStream topWordsStream;

    @GetMapping("/tweets")
    public Flux<Tweet> streamTweets() {
        log.info("Streaming tweets");
        return tweetStream.getTweets();
    }

    @GetMapping("/tweet-stats")
    public Flux<TweetStats> streamStats() {
        log.info("Streaming stats");
        return tweetStatsStream.getTweetStats();
    }

    @GetMapping("/top-words")
    public Flux<TopWords> streamTopWords() {
        log.info("Streaming top words");
        return topWordsStream.getTopWords();
    }

    @ExceptionHandler(IOException.class)
    public void handleException(IOException e) {
        log.warn("IOException ocurred: {}", e.getMessage());
    }
}
