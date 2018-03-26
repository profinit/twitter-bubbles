package cz.profinit.twitterbubbles.processing;

import cz.profinit.twitterbubbles.streaming.TweetStatsStream;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

@RequiredArgsConstructor
public class FluxStatsWriter implements CommandLineRunner {

    private final TweetStatsStream tweetStatsStream;

    @Override
    public void run(String... args) {
        tweetStatsStream.getTweetStats().subscribe(System.out::println);
    }
}
