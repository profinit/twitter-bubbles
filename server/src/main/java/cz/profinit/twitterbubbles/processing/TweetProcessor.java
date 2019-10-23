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
        Map <String, Integer> map = new HashMap();

        // TODO Rozdělení textu do slov a spočítání počtu jejich výskytů.

        String[] words = text.toLowerCase().split(" ");
        for ( String word  : words){
            word = word.replaceAll("[\\W]|_", "");
            //word = word.replaceAll("\\.", "");
            if (!word.equals("")){
                map.merge(word, 1,(a,b) -> a+b);
            }
        }
        // TODO Implementace je hotová, pokud uspěje unit test TweeProcessorTest.

        return map;
    }
}
