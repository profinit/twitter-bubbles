package cz.profinit.twitterbubbles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TwitterBubblesProperties {

    private final int minWordLength;
    private final int tweetStatsCountToTriggerTopWordsUpdate;
    private final int topWordCountToKeep;
    private final int topWordCountToTriggerPruning;

    private static final Logger log = LoggerFactory.getLogger(TwitterBubblesProperties.class);

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

    public int getMinWordLength() {
        return minWordLength;
    }

    public int getTweetStatsCountToTriggerTopWordsUpdate() {
        return tweetStatsCountToTriggerTopWordsUpdate;
    }

    public int getTopWordCountToKeep() {
        return topWordCountToKeep;
    }

    public int getTopWordCountToTriggerPruning() {
        return topWordCountToTriggerPruning;
    }

    @PostConstruct
    public void log() {
        log.info("{}", toString());
    }

    @Override
    public String toString() {
        return "TwitterBubblesProperties{" +
                "minWordLength=" + minWordLength +
                ", tweetStatsCountToTriggerTopWordsUpdate=" + tweetStatsCountToTriggerTopWordsUpdate +
                ", topWordCountToKeep=" + topWordCountToKeep +
                ", topWordCountToTriggerPruning=" + topWordCountToTriggerPruning +
                '}';
    }
}
