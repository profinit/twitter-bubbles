package cz.profinit.twitterbubbles.client;

import cz.profinit.twitterbubbles.model.TweetStats;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

public class StandaloneClientApplication {

    public static void main(String[] args) throws InterruptedException {
        Flux<TweetStats> flux = WebClient.create("http://localhost:8080/tweet-stats")
                .method(HttpMethod.GET)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(TweetStats.class);

        System.out.println("Starting streaming");

        Disposable subscription = flux.subscribe(System.out::println);

        Thread.sleep(5000);

        // TODO cancel subscription, this doesn't seem to be enough
        subscription.dispose();

        System.out.println("Streaming finished");
    }
}
