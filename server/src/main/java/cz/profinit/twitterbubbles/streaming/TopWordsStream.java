package cz.profinit.twitterbubbles.streaming;

import cz.profinit.twitterbubbles.model.TopWords;
import lombok.Data;
import reactor.core.publisher.Flux;

@Data(staticConstructor = "of")
public class TopWordsStream {

    private final Flux<TopWords> topWords;
}
