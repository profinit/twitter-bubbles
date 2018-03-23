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

        return new TweetStats(wordCounts);
    }

    List<String> words(String text) {
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            // You may want to check for a non-word character before blindly
            // performing a replacement
            // It may also be necessary to adjust the character class
            words[i] = words[i].replaceAll("[^\\w]", "");
        }
        return Arrays.asList(words);
    }

    Map<String, Integer> countWords(List<String> words) {
        return words.stream().map(String::toLowerCase).collect(groupingBy(word -> word, reducing(0, e -> 1, Integer::sum)));
    }
}
