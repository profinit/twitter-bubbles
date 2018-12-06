package cz.profinit.twitterbubbles.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

@Component
public class TweetProcessor {

    private static final Logger log = LoggerFactory.getLogger(TweetProcessor.class);
    private final AtomicInteger counter = new AtomicInteger(1);

    public Map<String, Integer> processTweetText(String text) {
        log.trace("Processing tweet number {}", counter.getAndIncrement());

        List<String> words = splitTextToWords(text);

        return countWords(words);
    }

    private Map<String, Integer> countWords(List<String> words) {
        return words.stream()
                .map(String::toLowerCase)
                .collect(groupingBy(word -> word, reducing(0, w -> 1, Integer::sum)));
    }

    private List<String> splitTextToWords(String text) {
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
        }
        return Arrays.asList(words);
    }
}
