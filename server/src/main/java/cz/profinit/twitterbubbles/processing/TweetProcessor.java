package cz.profinit.twitterbubbles.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
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

        Map<String, Integer> map = new HashMap<>();
        String[] array = text.split(" ");
        for (String word: array){
            String edited = "";
            for (char actualChar: word.toCharArray()){
                if (Character.isLetter(actualChar) || Character.isDigit(actualChar) || actualChar == '_'){
                    edited += actualChar;
                }
            }
            word = edited;
            word = word.toLowerCase();
            if (map.get(word) == null) {
                map.put(word,1);
            }
            else {
                map.put(word,map.get(word)+1);
            }
        }
        // TODO Rozdělení textu do slov a spočítání počtu jejich výskytů.
        // TODO Implementace je hotová, pokud uspěje unit test TweeProcessorTest.

        return map;
    }
}
