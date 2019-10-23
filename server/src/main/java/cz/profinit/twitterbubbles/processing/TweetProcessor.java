package cz.profinit.twitterbubbles.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

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

        // TODO Rozdělení textu do slov a spočítání počtu jejich výskytů.
        // TODO Implementace je hotová, pokud uspěje unit test TweeProcessorTest.

        String[] words = text.toLowerCase().split("[\\p{Punct}\\s]+");
        Map<String, Integer> stats = new HashMap<>();

        for(int i=0; i<words.length; i++) {
            if(!stats.containsKey(words[i])) {
                stats.put(words[i], 1);
            } else stats.merge(words[i], 1 , (a, b) -> a+b);
        }

        /*Map<String, Integer> stats = Arrays.stream(words).collect(
                groupingBy(
                        word ->word,
                        reducing(0, e->1, Integer::sum)));*/

        return stats;
    }
}
