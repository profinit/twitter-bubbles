package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.model.TweetStats;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Data(staticConstructor = "of")
@Slf4j
public class TweetStatsStream {

    private final Flux<TweetStats> tweetStats;

    public static String getLoggerName() {
        return log.getName();
    }
}
