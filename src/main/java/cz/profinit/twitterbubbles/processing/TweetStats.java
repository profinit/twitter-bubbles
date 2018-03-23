package cz.profinit.twitterbubbles.processing;

import lombok.Data;

import java.util.Map;

@Data
public class TweetStats {

    private final Map<String, Integer> wordCounts;
}
