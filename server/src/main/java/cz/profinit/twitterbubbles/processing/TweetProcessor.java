package cz.profinit.twitterbubbles.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Component
public class TweetProcessor {

    private static final Logger log = LoggerFactory.getLogger(TweetProcessor.class);

    private String wordParser(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetterOrDigit(c) || c == '_') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

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

        Map<String, Integer> counts = new HashMap<>();

        for (String word : text.split(" ")) {
            String cleanWord = wordParser(word);
            if (cleanWord.length() > 0) {
                String lowerCaseWord = cleanWord.toLowerCase();
                if (counts.containsKey(lowerCaseWord)) {
                    counts.put(lowerCaseWord, counts.get(lowerCaseWord) + 1);
                } else {
                    counts.put(lowerCaseWord, 1);
                }
            }
        }

        return counts;
    }
}
