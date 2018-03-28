package cz.profinit.twitterbubbles.client;

import cz.profinit.twitterbubbles.model.TopWords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
public class StandaloneClientApplication {

    public static void main(String[] args) throws InterruptedException {
//        Flux<TweetStats> tweetStatsFlux = WebClient.create("http://localhost:8080/tweet-stats")
//                .method(HttpMethod.GET)
//                .accept(MediaType.TEXT_EVENT_STREAM)
//                .retrieve()
//                .bodyToFlux(TweetStats.class);

        Flux<TopWords> topWordsFlux = WebClient.create("http://localhost:8080/top-words")
                .method(HttpMethod.GET)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(TopWords.class);

        System.out.println("Starting streaming");

//        Disposable subscription = tweetStatsFlux.subscribe(System.out::println);

        topWordsFlux.subscribe(System.out::println);

        Thread.sleep(10000);

        // TODO cancel subscription, this doesn't seem to be enough
//        subscription.dispose();

        System.out.println("Streaming finished");
    }
}
