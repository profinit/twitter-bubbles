package cz.profinit.twitterbubbles.client;

import cz.profinit.twitterbubbles.model.TopWords;
import cz.profinit.twitterbubbles.model.TweetStats;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class StandaloneClientApplication {

    public static void main(String[] args) throws InterruptedException {
        Flux<TweetStats> tweetStatsFlux = WebClient.create("http://localhost:8080/twitter-bubbles/tweet-stats")
                .method(HttpMethod.GET)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(TweetStats.class);

        AtomicInteger tweetStatsCounter = new AtomicInteger(1);

//        Disposable tweetStatsSubscription = tweetStatsFlux.subscribe(tweetStats ->
//                System.out.println(tweetStatsCounter.getAndIncrement() + ": " + tweetStats));

        Flux<TopWords> topWordsFlux = WebClient.create("http://localhost:8080/twitter-bubbles/top-words")
                .method(HttpMethod.GET)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(TopWords.class);

        AtomicInteger topWordsCounter = new AtomicInteger(1);

        Disposable topWordsSubscription = topWordsFlux.subscribe(topWords ->
                System.out.println(topWordsCounter.getAndIncrement() + ": " + topWords));

        System.out.println("Starting streaming");

        while (tweetStatsCounter.get() < 1000 || topWordsCounter.get() < 1000) {
        }

//        tweetStatsSubscription.dispose();
        topWordsSubscription.dispose();

        Thread.sleep(1000);

        System.out.println("Streaming finished");
    }
}
