package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.processing.TwitterStreamListenerSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.social.twitter.api.Tweet;
import reactor.core.publisher.FluxSink;

@RequiredArgsConstructor
public class TwitterToFluxStreamListener extends TwitterStreamListenerSupport {

    private final FluxSink<Tweet> sink;

    @Override
    public void onTweet(Tweet tweet) {
        sink.next(tweet);
    }
}
