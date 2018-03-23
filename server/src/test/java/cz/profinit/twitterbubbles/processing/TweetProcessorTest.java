package cz.profinit.twitterbubbles.processing;

import cz.profinit.twitterbubbles.model.TweetStats;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;
import org.springframework.social.twitter.api.Tweet;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TweetProcessorTest {

    private static final WordSplitTestCase[] testCases = {
            WordSplitTestCase.of(
                    "Kobyla ma maly bok.",
                    asList("Kobyla", "ma", "maly", "bok"),
                    new HashMap<String, Integer>() {{
                        put("kobyla", 1);
                        put("ma", 1);
                        put("maly", 1);
                        put("bok", 1);
                    }}),
            WordSplitTestCase.of(
                    "A man, a plan, a canal: Panama!",
                    asList("A", "man", "a", "plan", "a", "canal", "Panama"),
                    new HashMap<String, Integer>() {{
                        put("a", 3);
                        put("man", 1);
                        put("plan", 1);
                        put("canal", 1);
                        put("panama", 1);
                    }}),
            WordSplitTestCase.of(
                    "One little, two little, three little Indians, " +
                            "Four little, five little, six little Indians, " +
                            "Seven little, eight little, nine little Indians, " +
                            "Ten little Indian boys.",
                    asList("One", "little", "two", "little", "three", "little", "Indians",
                            "Four", "little", "five", "little", "six", "little", "Indians",
                            "Seven", "little", "eight", "little", "nine", "little", "Indians",
                            "Ten", "little", "Indian", "boys"),
                    new HashMap<String, Integer>() {{
                        put("one", 1);
                        put("two", 1);
                        put("three", 1);
                        put("four", 1);
                        put("five", 1);
                        put("six", 1);
                        put("seven", 1);
                        put("eight", 1);
                        put("nine", 1);
                        put("ten", 1);
                        put("little", 10);
                        put("indians", 3);
                        put("indian", 1);
                        put("boys", 1);
                    }}
            )
    };

    private TweetProcessor processor;

    @Before
    public void setUp() {
        processor = new TweetProcessor();
    }

    @Test
    public void shouldCountWords() {
        for (WordSplitTestCase testCase : testCases) {
            Map<String, Integer> wordCounts = processor.countWords(testCase.words);

            assertEquals("Expected different word counts", testCase.wordCounts, wordCounts);
        }
    }

    @Test
    public void shouldSplitTextIntoWords() {
        for (WordSplitTestCase testCase : testCases) {
            List<String> words = processor.words(testCase.text);

            assertEquals("Expected different words", testCase.words, words);
        }
    }

    @Test
    public void shouldProcessTweet() {
        for (WordSplitTestCase testCase : testCases) {
            Tweet tweet = new Tweet(1L, testCase.text, new Date(), "user", "url", 1L, 1L, "x", "s");
            TweetStats tweetStats = processor.processTweet(tweet);

            assertNotNull("Null tweet stats", tweetStats);
            assertEquals("Expected different word counts", testCase.wordCounts, tweetStats.getWordCounts());
        }
    }

    @Data(staticConstructor = "of")
    private static class WordSplitTestCase {

        private final String text;
        private final List<String> words;
        private final Map<String, Integer> wordCounts;
    }
}