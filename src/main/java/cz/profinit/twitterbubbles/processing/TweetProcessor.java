package cz.profinit.twitterbubbles.processing;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

@Component
public class TweetProcessor {

    public TweetStats processTweet(Tweet tweet) {
        String text = tweet.getText();

        List<String> words = words(text);

        Map<String, Integer> wordCounts = countWords(words);

        return new TweetStats(text, wordCounts);
    }

    List<String> words(String text) {
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
        }
        return Arrays.asList(words);
    }

    Map<String, Integer> countWords(List<String> words) {
        return words.stream().map(String::toLowerCase).collect(groupingBy(word -> word, reducing(0, e -> 1, Integer::sum)));
    }
}
