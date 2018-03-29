package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.model.TweetStats;
import cz.profinit.twitterbubbles.processing.TweetProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TweetStatsStreamFactory {

    private final TweetStream tweetStream;
    private final TweetProcessor processor;

    @Bean
    public TweetStatsStream tweetStatsStream() {
        log.info("Creating tweet stats stream");

        Flux<TweetStats> flux = tweetStream.getTweets()
                .map(processor::processTweet)
                .log(TweetStatsStream.getLoggerName());

        return TweetStatsStream.of(flux);
    }
}
