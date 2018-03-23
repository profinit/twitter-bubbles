package cz.profinit.twitterbubbles.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
public class TwitterTemplateConfiguration {

    @Bean
    public TwitterTemplate twitterTemplate(@Value("${spring.social.twitter.appId}") String consumerKey,
                                           @Value("${spring.social.twitter.appSecret}") String consumerSecret,
                                           @Value("${twitter.access.token}") String accessToken,
                                           @Value("${twitter.access.token.secret}") String accessTokenSecret) {

        return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
}
