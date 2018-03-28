package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.model.TopWords;
import cz.profinit.twitterbubbles.processing.WordCountProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Component
@Slf4j
public class TopWordsStreamFactory extends AbstractFactoryBean<TopWordsStream> {

    @Autowired
    private TweetStatsStream tweetStatsStream;
    @Autowired
    private WordCountProcessor wordCountProcessor;

    @Override
    protected TopWordsStream createInstance() {
        log.info("Creating top word stream");

        Flux<TopWords> flux = Flux.<TopWords>create(
                sink ->
                        tweetStatsStream.getTweetStats().subscribe(tweetStats -> {
                            wordCountProcessor.processTweetStats(tweetStats);
                            if (wordCountProcessor.getProcessedTweetStatsCount() % 10 == 0) {
                                sink.next(wordCountProcessor.getTopWords());
                            }
                        }),
                FluxSink.OverflowStrategy.DROP)
                .log();

        return TopWordsStream.of(flux);
    }

    @Override
    public Class<?> getObjectType() {
        return TopWordsStream.class;
    }
}
