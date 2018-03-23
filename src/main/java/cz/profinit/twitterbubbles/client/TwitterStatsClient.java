package cz.profinit.twitterbubbles.client;

import cz.profinit.twitterbubbles.processing.TweetStats;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class TwitterStatsClient implements CommandLineRunner {

    @Override
    public void run(String... args) {
        Flux<TweetStats> flux = WebClient.create("http://localhost:8080/tweet-stats")
                .method(HttpMethod.GET)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(TweetStats.class);

        flux.subscribe(System.out::println);
    }
}
