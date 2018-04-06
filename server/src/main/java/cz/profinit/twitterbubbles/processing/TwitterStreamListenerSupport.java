package cz.profinit.twitterbubbles.processing;

import org.slf4j.Logger;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;

public class TwitterStreamListenerSupport implements StreamListener {

    private final Logger log;

    public TwitterStreamListenerSupport(Logger log) {
        this.log = log;
    }

    @Override
    public void onTweet(Tweet tweet) {
    }

    @Override
    public void onDelete(StreamDeleteEvent deleteEvent) {
    }

    @Override
    public void onLimit(int numberOfLimitedTweets) {
        log.warn("Number of limited tweets: {}", numberOfLimitedTweets);
    }

    @Override
    public void onWarning(StreamWarningEvent warningEvent) {
        log.warn("Warning. Code: {}, message: {}, percent full: {}",
                warningEvent.getCode(), warningEvent.getMessage(), warningEvent.getPercentFull());
    }
}
