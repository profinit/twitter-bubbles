package cz.profinit.twitterbubbles.streaming;

import lombok.Data;
import org.springframework.social.twitter.api.Tweet;
import reactor.core.publisher.Flux;

@Data(staticConstructor = "of")
public class TweetStream {

    private final Flux<Tweet> tweets;
}
