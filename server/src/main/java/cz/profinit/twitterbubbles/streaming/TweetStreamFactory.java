package cz.profinit.twitterbubbles.streaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.Tweet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Configuration
public class TweetStreamFactory {

    private final TwitterListener twitterListener;

    private static final Logger log = LoggerFactory.getLogger(TweetStreamFactory.class);

    public TweetStreamFactory(TwitterListener twitterListener) {
        this.twitterListener = twitterListener;
    }

    @Bean
    public TweetStream tweetStream() {
        log.info("Creating tweet stream");

        Flux<Tweet> flux = Flux.<Tweet>create(twitterListener::addSink, FluxSink.OverflowStrategy.LATEST)
                .log(TweetStream.getLoggerName());

        return TweetStream.of(flux);
    }
}
