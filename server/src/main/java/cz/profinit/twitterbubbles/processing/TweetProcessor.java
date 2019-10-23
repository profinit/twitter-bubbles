package cz.profinit.twitterbubbles.processing;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

@Component
public class TweetProcessor {

    private static final Logger log = LoggerFactory.getLogger(TweetProcessor.class);

    /**
     * Splits the provided text into words and counts their occurrences.
     *
     * @param text Tweet text.
     * @return Word to occurrence count map. When no words founds, the method should return an empty map, but never {@code null}.
     */
    public Map<String, Integer> processTweetText(String text) {
        log.trace("Processing tweet");

        String trimmedText = text.replaceAll("[^A-Za-z0-9 ]", "");
        return Arrays.stream(trimmedText.split("[_,\\- ]"))
                .map(String::toLowerCase)
                .collect(groupingBy(Function.identity(), reducing(0, e -> 1, Integer::sum)));
    }
}
