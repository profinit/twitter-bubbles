package cz.profinit.twitterbubbles.controller;

import cz.profinit.twitterbubbles.model.TopWords;
import cz.profinit.twitterbubbles.streaming.TopWordsFluxFactory;
import cz.profinit.twitterbubbles.streaming.TweetStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@CrossOrigin
public class StreamController {

    private final TweetStream tweetStream;
    private final TopWordsFluxFactory topWordsFluxFactory;

    private static final Logger log = LoggerFactory.getLogger(StreamController.class);

    public StreamController(TweetStream tweetStream, TopWordsFluxFactory topWordsFluxFactory) {
        this.tweetStream = tweetStream;
        this.topWordsFluxFactory = topWordsFluxFactory;
    }

    @GetMapping("/tweets")
    public Flux<Tweet> streamTweets() {
        log.info("Streaming tweets");
        return tweetStream.getTweets();
    }

    @GetMapping("/top-words")
    public Flux<TopWords> streamTopWords() {
        log.info("Streaming top words");
        return topWordsFluxFactory.createTopWordsFlux();
    }

    @ExceptionHandler(IOException.class)
    public void handleException(IOException e) {
        log.warn("IOException occurred: {}", e.getMessage());
    }
}
