package cz.profinit.twitterbubbles.model;

import java.util.Map;

public class TopWords {

    private Map<String, Integer> topWords;

    public TopWords() {
    }

    public TopWords(Map<String, Integer> topWords) {
        this.topWords = topWords;
    }

    public Map<String, Integer> getTopWords() {
        return topWords;
    }
}
