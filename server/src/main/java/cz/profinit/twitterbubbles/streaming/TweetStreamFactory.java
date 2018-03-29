package cz.profinit.twitterbubbles.streaming;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.Tweet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TweetStreamFactory {

    private final TwitterListener twitterListener;

    @Bean
    public TweetStream tweetStream() {
        log.info("Creating tweet stream");

        Flux<Tweet> flux = Flux.<Tweet>create(twitterListener::addSink, FluxSink.OverflowStrategy.LATEST)
                .log(TweetStream.getLoggerName());

        return TweetStream.of(flux);
    }
}
