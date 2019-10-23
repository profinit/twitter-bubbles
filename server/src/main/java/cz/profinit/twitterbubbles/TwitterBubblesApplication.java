package cz.profinit.twitterbubbles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:git.properties")
public class TwitterBubblesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterBubblesApplication.class, args);
	}
}

// http://localhost:8090/twitter-bubbles/
