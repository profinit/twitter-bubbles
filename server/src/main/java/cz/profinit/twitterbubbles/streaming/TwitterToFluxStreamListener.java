package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.processing.TwitterStreamListenerSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.twitter.api.Tweet;
import reactor.core.publisher.FluxSink;

@Slf4j
public class TwitterToFluxStreamListener extends TwitterStreamListenerSupport {

    private final FluxSink<Tweet> sink;

    private int counter = 1;

    public TwitterToFluxStreamListener(FluxSink<Tweet> sink) {
        super(log);
        this.sink = sink;
    }

    @Override
    public void onTweet(Tweet tweet) {
        log.trace("Putting tweet number {} to sink. Id: {}", counter++, tweet.getId());
        sink.next(tweet);
    }
}
