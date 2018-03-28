package cz.profinit.twitterbubbles.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopWords {

    private List<WordCount> topWords;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WordCount {

        private String word;
        private int count;
    }
}
