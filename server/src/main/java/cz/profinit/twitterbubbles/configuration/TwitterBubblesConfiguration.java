package cz.profinit.twitterbubbles.configuration;

import cz.profinit.twitterbubbles.processing.FluxStatsWriter;
import cz.profinit.twitterbubbles.streaming.TweetStatsStream;
import cz.profinit.twitterbubbles.writer.SimpleTweetWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
public class TwitterBubblesConfiguration {

    @Bean
    @Profile("simpleTweetWriter")
    public SimpleTweetWriter simpleTweetWriter(TwitterTemplate twitterTemplate) {
        return new SimpleTweetWriter(twitterTemplate);
    }

    @Bean
    @Profile("fluxStatsWriter")
    public FluxStatsWriter fluxStatsWriter(TweetStatsStream tweetStatsStream) {
        return new FluxStatsWriter(tweetStatsStream);
    }
}
