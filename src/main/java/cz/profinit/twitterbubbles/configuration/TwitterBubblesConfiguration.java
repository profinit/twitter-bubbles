package cz.profinit.twitterbubbles.configuration;

import cz.profinit.twitterbubbles.client.TwitterStatsClient;
import cz.profinit.twitterbubbles.processing.FluxStatsWriter;
import cz.profinit.twitterbubbles.streaming.TweetStatsFluxFactory;
import cz.profinit.twitterbubbles.writer.SimpleTweetWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
public class TwitterBubblesConfiguration {

    @Bean
    @Profile("writer")
    public SimpleTweetWriter simpleTweetWriter(TwitterTemplate twitterTemplate) {
        return new SimpleTweetWriter(twitterTemplate);
    }

    @Bean
    @Profile("fluxStatsWriter")
    public FluxStatsWriter fluxStatsWriter(TweetStatsFluxFactory dummy) {
        return new FluxStatsWriter(dummy);
    }

    @Bean
    @Profile("standaloneClient")
    public TwitterStatsClient twitterStatsClient() {
        return new TwitterStatsClient();
    }
}
