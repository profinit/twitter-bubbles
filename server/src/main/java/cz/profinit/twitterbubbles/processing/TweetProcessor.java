package cz.profinit.twitterbubbles.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
        String words[] = text.split(" ");
        Map<String, Integer> map = new HashMap<>();
        Integer n = 1;
        for (String word : words) {
            word = word.toLowerCase().replaceAll("[^a-zA-Z0-9]","");
            if (map.get(word) != null) {
                Integer count = map.get(word);
                map.put(word, count+1);
            }
            else {
                map.put(word,1);
            }
        }

        // TODO Implementace je hotová, pokud uspěje unit test TweeProcessorTest.

        return map;
    }
}
