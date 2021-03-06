package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.TwitterBubblesProperties;
import cz.profinit.twitterbubbles.model.TopWords;
import cz.profinit.twitterbubbles.model.TweetStats;
import cz.profinit.twitterbubbles.processing.TweetProcessor;
import cz.profinit.twitterbubbles.processing.WordCountAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class TopWordsFluxFactory {

    private static final Logger log = LoggerFactory.getLogger(TopWordsFluxFactory.class);
    private final TweetStream tweetStream;
    private final TweetProcessor tweetProcessor;
    private final TwitterBubblesProperties properties;

    public TopWordsFluxFactory(TweetStream tweetStream, TweetProcessor tweetProcessor, TwitterBubblesProperties properties) {
        this.tweetStream = tweetStream;
        this.tweetProcessor = tweetProcessor;
        this.properties = properties;
    }

    public Flux<TopWords> createTopWordsFlux() {
        log.info("Creating top word stream");

        return Flux.just(new WordCountAggregator(properties))
                .repeat()
                .zipWith(tweetStream.getTweets()
                                .map(Tweet::getText)
                                .map(tweetProcessor::processTweetText)
                                .map(TweetStats::new)
                                .window(properties.getTweetStatsCountToTriggerTopWordsUpdate())
                                .flatMap(flux -> flux.reduce(TweetStats.EMPTY, TweetStats::merge)),
                        WordCountAggregator::aggregateTweetStats)
                .share();
    }
}
