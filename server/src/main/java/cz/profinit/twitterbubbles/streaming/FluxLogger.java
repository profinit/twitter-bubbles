package cz.profinit.twitterbubbles.streaming;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
public class FluxLogger {

    public static Logger getLog() {
        return log;
    }

    public static String getCategory() {
        return log.getName();
    }
}
