package cz.profinit.twitterbubbles.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

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

        Map <String, Integer> result = new HashMap<>();

        text = text.toLowerCase();
        text = text.replaceAll("[^a-zA-Z0-9]", " ");
        String[] words = text.split(" ");

        for (String word : words)
        {
            if (!word.equals("") && !word.equals("https"))
                if (result.containsKey(word))
                    result.put(word, result.get(word)+1);
                else
                    result.put(word, 1);
        }

        // TODO Rozdělení textu do slov a spočítání počtu jejich výskytů.
        // TODO Implementace je hotová, pokud uspěje unit test TweeProcessorTest.

        return result;
    }
}
