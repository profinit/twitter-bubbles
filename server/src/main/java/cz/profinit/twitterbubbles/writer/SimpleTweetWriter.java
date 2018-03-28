package cz.profinit.twitterbubbles.writer;

import cz.profinit.twitterbubbles.processing.TwitterStreamListenerSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class SimpleTweetWriter implements CommandLineRunner {

    private final TwitterTemplate twitterTemplate;

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
