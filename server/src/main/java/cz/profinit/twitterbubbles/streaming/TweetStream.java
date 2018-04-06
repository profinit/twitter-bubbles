package cz.profinit.twitterbubbles.streaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Tweet;
import reactor.core.publisher.Flux;

public class TweetStream {

    private final Flux<Tweet> tweets;

    private static final Logger log = LoggerFactory.getLogger(TweetStream.class);

    public static TweetStream of(Flux<Tweet> tweets) {
        return new TweetStream(tweets);
    }

    private TweetStream(Flux<Tweet> tweets) {
        this.tweets = tweets;
    }

    public Flux<Tweet> getTweets() {
        return tweets;
    }

    public static String getLoggerName() {
        return log.getName();
    }
}
