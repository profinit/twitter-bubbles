package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.TwitterBubblesProperties;
import cz.profinit.twitterbubbles.model.TopWords;
import cz.profinit.twitterbubbles.processing.WordCountProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TopWordsStreamFactory {

    private final TweetStatsStream tweetStatsStream;
    private final TwitterBubblesProperties properties;

    @Bean
    public TopWordsStream topWordsStream() {
        log.info("Creating top word stream");

        WordCountProcessor wordCountProcessor = new WordCountProcessor(properties);

        Flux<TopWords> flux = tweetStatsStream.getTweetStats()
                .map(tweetStats -> {
                    wordCountProcessor.processTweetStats(tweetStats);
                    return wordCountProcessor.getTopWords();
                })
                .filter(topWords -> topWords.getIndex() % 10 == 0)
                .log(TopWordsStream.getLoggerName());

        return TopWordsStream.of(flux);
    }
}
