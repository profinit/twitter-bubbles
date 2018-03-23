package cz.profinit.twitterbubbles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
public class TwitterBubblesConfiguration {

    @Bean
    @Profile("writer")
    public SimpleMessageWriter simpleMessageWriter(TwitterTemplate twitterTemplate) {
        return new SimpleMessageWriter(twitterTemplate);
    }
}
