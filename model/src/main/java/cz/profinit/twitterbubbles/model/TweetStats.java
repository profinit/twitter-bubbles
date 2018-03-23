package cz.profinit.twitterbubbles.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetStats {

    private String text;
    private Map<String, Integer> wordCounts;
}
