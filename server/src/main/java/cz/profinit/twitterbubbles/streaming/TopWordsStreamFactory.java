package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.TwitterBubblesProperties;
import cz.profinit.twitterbubbles.model.TopWords;
import cz.profinit.twitterbubbles.model.TweetStats;
import cz.profinit.twitterbubbles.processing.TweetProcessor;
import cz.profinit.twitterbubbles.processing.WordCountProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
@Slf4j
public class TopWordsStreamFactory {

    private final TweetStream tweetStream;
    private final TweetProcessor tweetProcessor;
    private final TwitterBubblesProperties properties;

    public Flux<TopWords> createTopWordsStream() {
        log.info("Creating top word stream");

        WordCountProcessor wordCountProcessor = new WordCountProcessor(properties);

        return tweetStream.getTweets()
                .map(tweetProcessor::processTweet)
                .window(properties.getTweetStatsCountToTriggerTopWordsUpdate())
                .flatMap(tweetStatsFlux -> tweetStatsFlux.reduce(TweetStats.EMPTY, TweetStats::merge))
                .map(wordCountProcessor::processTweetStats);
    }
}
