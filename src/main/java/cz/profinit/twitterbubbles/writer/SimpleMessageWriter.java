package cz.profinit.twitterbubbles.writer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class SimpleMessageWriter implements CommandLineRunner {

    private final TwitterTemplate twitterTemplate;

    @Override
    public void run(String... args) {
        StreamListener streamListener = new StreamListener() {
            @Override
            public void onDelete(StreamDeleteEvent deleteEvent) {

            }

            @Override
            public void onLimit(int numberOfLimitedTweets) {

            }

            @Override
            public void onTweet(Tweet tweet) {
                String hashTags = tweet.getEntities().getHashTags().stream()
                        .map(hashTagEntity -> "#" + hashTagEntity.getText())
                        .collect(Collectors.joining(", "));

                String info = String.format("Created at: %s, user: %s, text: %s, hash tags: %s",
                        tweet.getCreatedAt(), tweet.getFromUser(), tweet.getText(), hashTags);

                System.out.println(info);
            }

            @Override
            public void onWarning(StreamWarningEvent warningEvent) {

            }
        };

        log.info("Starting streaming");

        twitterTemplate.streamingOperations().sample(Collections.singletonList(streamListener));
    }
}
