package cz.profinit.twitterbubbles.processing;

import cz.profinit.twitterbubbles.model.TweetStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

@Component
public class TweetProcessor {

    private static final Logger log = LoggerFactory.getLogger(TweetProcessor.class);

    public TweetStats processTweet(Tweet tweet) {
        log.trace("Processing tweet: {}", tweet.getText());

        String text = tweet.getText();

        // TODO Rozdělení textu do slov a spočítání počtu jejich výskytů.
        // TODO Implementace je hotová, pokud uspěje unit test TweeProcessorTest.

        return TweetStats.DUMMY;
    }
}
