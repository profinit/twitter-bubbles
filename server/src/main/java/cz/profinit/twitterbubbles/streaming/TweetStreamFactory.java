package cz.profinit.twitterbubbles.streaming;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.lang.Nullable;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static java.util.Collections.singletonList;

@Component
@RequiredArgsConstructor
@Slf4j
public class TweetStreamFactory extends AbstractFactoryBean<TweetStream> {

    @Autowired
    private TwitterTemplate twitterTemplate;

    @Override
    protected TweetStream createInstance() throws Exception {
        log.info("Creating tweet stream");

        Flux<Tweet> flux = Flux.create(sink ->
                twitterTemplate.streamingOperations().sample(singletonList(new TwitterToFluxStreamListener(sink))));

        return TweetStream.of(flux);
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {
        return TweetStream.class;
    }
}
