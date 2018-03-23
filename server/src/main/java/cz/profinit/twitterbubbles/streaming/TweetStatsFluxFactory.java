package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.model.TweetStats;
import cz.profinit.twitterbubbles.processing.TweetProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static java.util.Collections.singletonList;

@Component
@RequiredArgsConstructor
@Slf4j
public class TweetStatsFluxFactory {

    private final TweetProcessor processor;
    private final TwitterTemplate twitterTemplate;

    public Flux<TweetStats> createTweetStatsFlux() {
        return createTweetFlux().map(processor::processTweet);
    }

    private Flux<Tweet> createTweetFlux() {
        return Flux.create(sink ->
                twitterTemplate.streamingOperations().sample(singletonList(new TwitterToFluxStreamListener(sink))));
    }
}
