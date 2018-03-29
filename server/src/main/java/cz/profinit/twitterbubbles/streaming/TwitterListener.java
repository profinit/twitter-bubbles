package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.processing.TwitterStreamListenerSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.twitter.api.Stream;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

@Component
@Slf4j
public class TwitterListener extends TwitterStreamListenerSupport {

    private final TwitterTemplate twitterTemplate;

    private final List<FluxSink<Tweet>> sinks = new ArrayList<>();

    private Stream twitterStream;
    private int counter = 1;

    public TwitterListener(TwitterTemplate twitterTemplate) {
        super(log);
        this.twitterTemplate = twitterTemplate;
    }

    public synchronized void addSink(FluxSink<Tweet> sink) {
        log.debug("Adding sink: {}", sink);
        sinks.add(sink);
        if (sinks.size() == 1) {
            log.info("Streaming Twitter sample data");
            twitterStream = twitterTemplate.streamingOperations().sample(singletonList(this));
        }
    }

    public synchronized void removeSink(FluxSink<Tweet> sink) {
        log.debug("Removing sink: {}", sink);
        sinks.remove(sink);
        if (sinks.isEmpty() && twitterStream != null) {
            twitterStream.close();
            twitterStream = null;
        }
    }

    @Override
    public void onTweet(Tweet tweet) {
        log.trace("Putting tweet number {} to {} sinks. Id: {}", counter++, sinks.size(), tweet.getId());
        sinks.forEach(sink -> {
            if (!sink.isCancelled()) {
                sink.next(tweet);
            } else {
                removeSink(sink);
            }
        });
    }
}
