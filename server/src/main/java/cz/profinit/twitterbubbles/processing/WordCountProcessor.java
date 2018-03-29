package cz.profinit.twitterbubbles.processing;

import cz.profinit.twitterbubbles.model.TopWords;
import cz.profinit.twitterbubbles.model.TweetStats;
import lombok.Getter;
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
@Slf4j
public class WordCountProcessor {

    private static final int MIN_WORD_LENGTH = 3;
    private static final int TWEET_STATS_COUNT_TO_TRIGGER_TOP_WORD_UPDATE = 10;
    private static final int TOP_WORD_COUNT_TO_KEEP = 100;
    private static final int TOP_WORD_COUNT_TO_TRIGGER_PRUNING = 200;

    @Getter
    private final Map<String, Integer> wordCount = new HashMap<>();
    private final Comparator<String> wordCountComparator =
            (a, b) -> wordCount.getOrDefault(b, 0) - wordCount.getOrDefault(a, 0);
    private final Set<String> allWords = new HashSet<>();
    @Getter
    private int processedTweetStatsCount = 0;
    private List<String> topWords = Collections.emptyList();

    public TopWords getTopWords() {
        Map<String, Integer> topWordMap = topWords.subList(0, Math.min(TOP_WORD_COUNT_TO_KEEP, topWords.size())).stream()
                .collect(Collectors.toMap(Function.identity(), wordCount::get, (a, b) -> a, LinkedHashMap::new));

        return new TopWords(topWordMap);
    }

    public synchronized void processTweetStats(TweetStats tweetStats) {
        for (Map.Entry<String, Integer> entry : tweetStats.getWordCounts().entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();
            if (word.length() >= MIN_WORD_LENGTH) {
                wordCount.merge(word, count, (oldValue, value) -> oldValue + value);
                allWords.add(word);
            }
        }

        processedTweetStatsCount++;

        if (processedTweetStatsCount % TWEET_STATS_COUNT_TO_TRIGGER_TOP_WORD_UPDATE == 0) {
            topWords = new ArrayList<>(allWords);
            topWords.sort(wordCountComparator);

            if (topWords.size() > TOP_WORD_COUNT_TO_TRIGGER_PRUNING) {
                topWords.subList(TOP_WORD_COUNT_TO_KEEP, topWords.size()).forEach(word -> {
                    wordCount.remove(word);
                    allWords.remove(word);
                });
            }
        }
    }
}
