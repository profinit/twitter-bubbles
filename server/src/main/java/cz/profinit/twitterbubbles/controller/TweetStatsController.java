package cz.profinit.twitterbubbles.controller;

import cz.profinit.twitterbubbles.model.TweetStats;
import cz.profinit.twitterbubbles.streaming.TweetStatsStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TweetStatsController {

    private final TweetStatsStream tweetStatsStream;

    @GetMapping("/tweet-stats")
    public Flux<TweetStats> streamStats() {
        log.info("Streaming stats");
        return tweetStatsStream.getTweetStats();
    }
}
