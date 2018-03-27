package cz.profinit.twitterbubbles.processing;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class WordCountProcessor {

    private static final int MIN_WORD_LENGTH = 3;

    private final Map<String, Integer> totalWordCounts = new HashMap<>();
    private final Comparator<String> totalWordCountComparator =
            (a, b) -> totalWordCounts.getOrDefault(b, 0) - totalWordCounts.getOrDefault(a, 0);
    @Getter
    private final Set<String> allWords = new HashSet<>();
    private int x = 0;

    public synchronized void addWordCounts(Map<String, Integer> wordCounts) {
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            addWordCount(entry.getKey(), entry.getValue());
        }

        x++;
        if (x % 10 == 0) {
            ArrayList<String> topWords = new ArrayList<>(allWords);
            topWords.sort(totalWordCountComparator);
            log.info("{} {} {}", x, allWords.size(), topWords.subList(0, 20).stream().map(word -> word + ":" + totalWordCounts.get(word)).collect(Collectors.joining(", ")));

            if (topWords.size() > 100) {
                topWords.subList(100, topWords.size()).forEach(word -> {
                    totalWordCounts.remove(word);
                    allWords.remove(word);
                });
            }
        }
    }

    private void addWordCount(String word, int count) {
        if (word.length() >= MIN_WORD_LENGTH) {
            totalWordCounts.merge(word, count, (oldValue, value) -> oldValue + value);
            allWords.add(word);
        }
    }
}
