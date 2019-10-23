package cz.profinit.twitterbubbles.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        String[] split = text.toLowerCase().replaceAll("\\W", " ").replaceAll("\\s+", " ").split(" ");
        Set<String> filteredWords = new HashSet<>(Arrays.asList("https", "japan"));
        Map<String, Integer> map = Arrays.stream(split).filter(word -> !filteredWords.contains(word)).collect(groupingBy(word -> word, reducing(0, e -> 1, Integer::sum)));

//        for (String s : split) {
//            if ("https".equals(s)){
//                continue;
//            }
//            if (!map.containsKey(s)) {
//                map.put(s, 1);
//            } else {
//                int count = map.get(s);
//                map.put(s, count + 1);
//            }
//        }
        return map;
    }
}
