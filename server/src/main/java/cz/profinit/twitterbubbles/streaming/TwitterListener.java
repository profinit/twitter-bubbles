package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.processing.TwitterStreamListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Stream;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.FluxSink;

import javax.annotation.PreDestroy;

import static java.util.Collections.singletonList;

@Component
public class TwitterListener extends TwitterStreamListenerSupport {

    private final TwitterTemplate twitterTemplate;
    private FluxSink<Tweet> sink;
    private Stream twitterStream;
    private int counter = 1;

    private static final Logger log = LoggerFactory.getLogger(TwitterListener.class);

    public TwitterListener(TwitterTemplate twitterTemplate) {
        super(log);
        this.twitterTemplate = twitterTemplate;
    }

    public void start(FluxSink<Tweet> sink) {
        log.info("Streaming Twitter sample data");
        this.sink = sink;
        twitterStream = twitterTemplate.streamingOperations().sample(singletonList(this));
    }

    @PreDestroy
    public void stop() {
        log.info("Stopping streaming Twitter sample data");
        twitterStream.close();
    }

    @Override
    public void onTweet(Tweet tweet) {
        log.trace("Putting tweet number {} to sink. Id: {}", counter++, tweet.getId());
        sink.next(tweet);
    }
}
