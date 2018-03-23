package cz.profinit.twitterbubbles.processing;

import cz.profinit.twitterbubbles.streaming.TweetStatsFluxFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

@RequiredArgsConstructor
public class FluxStatsWriter implements CommandLineRunner {

    private final TweetStatsFluxFactory tweetStatsFluxFactory;

    @Override
    public void run(String... args) {
        tweetStatsFluxFactory.createTweetStatsFlux()
                .subscribe(System.out::println);
    }
}
