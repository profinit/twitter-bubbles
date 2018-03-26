package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.model.TweetStats;
import lombok.Data;
import reactor.core.publisher.Flux;

@Data(staticConstructor = "of")
public class TweetStatsStream {

    private final Flux<TweetStats> tweetStats;
}
