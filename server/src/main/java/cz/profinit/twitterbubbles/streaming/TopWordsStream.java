package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.model.TopWords;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Data(staticConstructor = "of")
@Slf4j
public class TopWordsStream {

    private final Flux<TopWords> topWords;

    public static String getLoggerName() {
        return log.getName();
    }
}
