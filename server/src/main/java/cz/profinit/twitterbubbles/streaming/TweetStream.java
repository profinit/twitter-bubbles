package cz.profinit.twitterbubbles.streaming;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.twitter.api.Tweet;
import reactor.core.publisher.Flux;

@Data(staticConstructor = "of")
@Slf4j
public class TweetStream {

    private final Flux<Tweet> tweets;

    public static String getLoggerName() {
        return log.getName();
    }
}
