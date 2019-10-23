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

    private static String getWord(String string){
        String result = "";
        for(int i = 0; i < string.length(); i++ ){
            if(Character.isLetter(string.charAt(i)) || Character.isDigit(string.charAt(i))){
                result += string.charAt(i);
            }
        }
        return result.toLowerCase();
    }

    public Map<String, Integer> processTweetText(String text) {
        log.trace("Processing tweet");

        String[] res = text.split(" ", 100);


        HashMap<String, Integer> map = new HashMap<String, Integer>();

        for(String item : res){
            String s = getWord(item);
            if(!map.containsKey(s)){
                map.put(s, 1);
            } else{
                map.put(s, map.get(s) + 1);

            }
        }

        // TODO Rozdělení textu do slov a spočítání počtu jejich výskytů.
        // TODO Implementace je hotová, pokud uspěje unit test TweeProcessorTest.

        return map;
    }
}
