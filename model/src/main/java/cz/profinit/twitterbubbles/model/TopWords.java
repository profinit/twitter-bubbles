package cz.profinit.twitterbubbles.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopWords {

    private Map<String, Integer> topWords;
}
