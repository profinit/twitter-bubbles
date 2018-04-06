package cz.profinit.twitterbubbles.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TweetStats {

    public static final TweetStats EMPTY = new TweetStats(Collections.emptyMap());

    public static final TweetStats DUMMY = new TweetStats(new HashMap<String, Integer>() {{
        put("dummy", 1);
    }});

    private Map<String, Integer> wordCounts;

    public TweetStats() {
    }

    public TweetStats(Map<String, Integer> wordCounts) {
        this.wordCounts = wordCounts;
    }

    public Map<String, Integer> getWordCounts() {
        return wordCounts;
    }

    public TweetStats merge(TweetStats tweetStats) {
        HashMap<String, Integer> mergedWordCounts = new HashMap<>(wordCounts);
        tweetStats.wordCounts.forEach((word, count) ->
                mergedWordCounts.merge(word, count, (count1, count2) -> count1 + count2));
        return new TweetStats(mergedWordCounts);
    }
}
