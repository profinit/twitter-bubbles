package cz.profinit.twitterbubbles.streaming;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import static java.util.Collections.singletonList;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TweetStreamFactory {

    private final TwitterTemplate twitterTemplate;

    @Bean
    public TweetStream tweetStream() {
        log.info("Creating tweet stream");

        Flux<Tweet> flux = Flux.<Tweet>create(
                sink -> {
                    log.info("Streaming Twitter sample data");
                    twitterTemplate.streamingOperations().sample(singletonList(new TwitterToFluxStreamListener(sink)));
                },
                FluxSink.OverflowStrategy.DROP)
                .log(TweetStream.getLoggerName());

        return TweetStream.of(flux);
    }
}
