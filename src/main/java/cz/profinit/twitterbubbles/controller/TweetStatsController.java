package cz.profinit.twitterbubbles.controller;

import cz.profinit.twitterbubbles.processing.TweetStats;
import cz.profinit.twitterbubbles.streaming.TweetStatsFluxFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class TweetStatsController {

    private final TweetStatsFluxFactory tweetStatsFluxFactory;

    @GetMapping("/tweet-stats")
    public Flux<TweetStats> streamStats() {
        return tweetStatsFluxFactory.createTweetStatsFlux();
    }
}
