package cz.profinit.twitterbubbles.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetStats {

    public static TweetStats EMPTY = new TweetStats(Collections.emptyMap());

    private Map<String, Integer> wordCounts;

    public TweetStats merge(TweetStats tweetStats) {
        HashMap<String, Integer> mergedWordCounts = new HashMap<>(wordCounts);
        tweetStats.wordCounts.forEach((word, count) ->
                mergedWordCounts.merge(word, count, (count1, count2) -> count1 + count2));
        return new TweetStats(mergedWordCounts);
    }
}
