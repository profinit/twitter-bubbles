package cz.profinit.twitterbubbles.writer;

import cz.profinit.twitterbubbles.processing.TwitterStreamListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.util.Collections;
import java.util.stream.Collectors;

public class SimpleTweetWriter implements CommandLineRunner {

    private final TwitterTemplate twitterTemplate;

    private static final Logger log = LoggerFactory.getLogger(SimpleTweetWriter.class);

    public SimpleTweetWriter(TwitterTemplate twitterTemplate) {
        this.twitterTemplate = twitterTemplate;
    }

    @Override
    public void run(String... args) {
        StreamListener streamListener = new TwitterStreamListenerSupport(log) {
            public void onTweet(Tweet tweet) {
                String hashTags = tweet.getEntities().getHashTags().stream()
                        .map(hashTagEntity -> "#" + hashTagEntity.getText())
                        .collect(Collectors.joining(", "));

                String info = String.format("Created at: %s, user: %s, text: %s, hash tags: %s",
                        tweet.getCreatedAt(), tweet.getFromUser(), tweet.getText(), hashTags);

                System.out.println(info);
            }
        };

        log.info("Starting streaming");

        twitterTemplate.streamingOperations().sample(Collections.singletonList(streamListener));
    }
}
