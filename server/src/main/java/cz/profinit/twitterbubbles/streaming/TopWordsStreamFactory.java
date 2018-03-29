package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.TwitterBubblesProperties;
import cz.profinit.twitterbubbles.model.TopWords;
import cz.profinit.twitterbubbles.processing.WordCountProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TopWordsStreamFactory {

    private final TweetStatsStream tweetStatsStream;
    private final TwitterBubblesProperties properties;

    @Bean
    public TopWordsStream topWordsStream() {
        log.info("Creating top word stream");

        Flux<TopWords> flux = Flux.<TopWords>create(
                sink -> {
                    WordCountProcessor wordCountProcessor = new WordCountProcessor(properties);

                    AtomicInteger counter = new AtomicInteger(1);

                    tweetStatsStream.getTweetStats().subscribe(tweetStats -> {
                        wordCountProcessor.processTweetStats(tweetStats);
                        if (wordCountProcessor.getProcessedTweetStatsCount() % properties.getTweetStatsCountToTriggerTopWordsUpdate() == 0) {
                            log.trace("Putting top words number {} to sink", counter.getAndIncrement());
                            sink.next(wordCountProcessor.getTopWords());
                        }
                    });
                },
                FluxSink.OverflowStrategy.DROP)
                .log(TopWordsStream.getLoggerName());

        return TopWordsStream.of(flux);
    }
}
