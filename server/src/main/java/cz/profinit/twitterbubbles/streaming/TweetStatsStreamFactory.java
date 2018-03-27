package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.model.TweetStats;
import cz.profinit.twitterbubbles.processing.TweetProcessor;
import cz.profinit.twitterbubbles.processing.WordCountProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
@Slf4j
public class TweetStatsStreamFactory extends AbstractFactoryBean<TweetStatsStream> {

    @Autowired
    private TweetStream tweetStream;
    @Autowired
    private TweetProcessor processor;
    @Autowired
    private WordCountProcessor wordCountProcessor;

    @Override
    protected TweetStatsStream createInstance() {
        log.info("Creating tweet stats stream");

        Flux<TweetStats> flux = tweetStream.getTweets().map(processor::processTweet);

        flux.subscribe(tweetStats -> wordCountProcessor.addWordCounts(tweetStats.getWordCounts()));

        return TweetStatsStream.of(flux);
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {
        return TweetStatsStream.class;
    }
}
