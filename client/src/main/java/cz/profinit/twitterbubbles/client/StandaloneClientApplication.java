package cz.profinit.twitterbubbles.client;

import cz.profinit.twitterbubbles.model.TweetStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Slf4j
public class StandaloneClientApplication {

    public static void main(String[] args) throws InterruptedException {
        Flux<TweetStats> flux = WebClient.create("http://localhost:8080/tweet-stats")
                .method(HttpMethod.GET)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(TweetStats.class);

        System.out.println("Starting streaming");

        Disposable subscription = flux.subscribe(tweetStats -> log.info("{}", tweetStats));

        Thread.sleep(5000);

        // TODO cancel subscription, this doesn't seem to be enough
//        subscription.dispose();

        System.out.println("Streaming finished");
    }
}
