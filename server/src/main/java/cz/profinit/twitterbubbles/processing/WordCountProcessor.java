package cz.profinit.twitterbubbles.processing;

import cz.profinit.twitterbubbles.TwitterBubblesProperties;
import cz.profinit.twitterbubbles.model.TopWords;
import cz.profinit.twitterbubbles.model.TweetStats;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class WordCountProcessor {

    private final TwitterBubblesProperties properties;

    @Getter
    private final Map<String, Integer> wordCount = new HashMap<>();
    private final Comparator<String> wordCountComparator =
            (a, b) -> wordCount.getOrDefault(b, 0) - wordCount.getOrDefault(a, 0);
    private final Set<String> allWords = new HashSet<>();
    @Getter
    private int processedTweetStatsCount = 0;
    private List<String> topWords = Collections.emptyList();

    public TopWords getTopWords() {
        Map<String, Integer> topWordMap = topWords.subList(0, Math.min(properties.getTopWordCountToKeep(), topWords.size())).stream()
                .collect(Collectors.toMap(Function.identity(), wordCount::get, (a, b) -> a, LinkedHashMap::new));

        return new TopWords(topWordMap);
    }

    public synchronized void processTweetStats(TweetStats tweetStats) {
        for (Map.Entry<String, Integer> entry : tweetStats.getWordCounts().entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();
            if (word.length() >= properties.getMinWordLength()) {
                wordCount.merge(word, count, (oldValue, value) -> oldValue + value);
                allWords.add(word);
            }
        }

        processedTweetStatsCount++;

        if (processedTweetStatsCount % properties.getTweetStatsCountToTriggerTopWordsUpdate() == 0) {
            topWords = new ArrayList<>(allWords);
            topWords.sort(wordCountComparator);

            if (topWords.size() > properties.getTopWordCountToTriggerPruning()) {
                topWords.subList(properties.getTopWordCountToKeep(), topWords.size()).forEach(word -> {
                    wordCount.remove(word);
                    allWords.remove(word);
                });
            }
        }
    }
}
