package cz.profinit.twitterbubbles.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
        HashMap<String, Integer> wordsCount = new HashMap<>();

        String[] splitted = (text.toLowerCase().split("[\\p{Punct}\\s]+"));

        for( int i = 0; i < splitted.length; i++) {
            if (splitted[i].equals("https")) {
                continue;
            }
            if( wordsCount.get(splitted[i]) != null) {
                wordsCount.put(splitted[i], wordsCount.get(splitted[i]) + 1);
            } else {
                wordsCount.put(splitted[i], 1);
            }
        }

        return wordsCount;
    }
}
