package cz.profinit.twitterbubbles;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Data
@Slf4j
public class TwitterBubblesProperties {

    private final int minWordLength;
    private final int tweetStatsCountToTriggerTopWordsUpdate;
    private final int topWordCountToKeep;
    private final int topWordCountToTriggerPruning;

    public TwitterBubblesProperties(
            @Value("${twitter.bubbles.minWordLength}") int minWordLength,
            @Value("${twitter.bubbles.tweetStatsCountToTriggerTopWordsUpdate}") int tweetStatsCountToTriggerTopWordsUpdate,
            @Value("${twitter.bubbles.topWordCountToKeep}") int topWordCountToKeep,
            @Value("${twitter.bubbles.topWordCountToTriggerPruning}") int topWordCountToTriggerPruning) {

        this.minWordLength = minWordLength;
        this.tweetStatsCountToTriggerTopWordsUpdate = tweetStatsCountToTriggerTopWordsUpdate;
        this.topWordCountToKeep = topWordCountToKeep;
        this.topWordCountToTriggerPruning = topWordCountToTriggerPruning;
    }

    @PostConstruct
    public void log() {
        log.info("{}", toString());
    }
}
