package cz.profinit.twitterbubbles.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private static final Logger log = LoggerFactory.getLogger(ViewController.class);

    @GetMapping("/")
    public String view() {
        log.info("Showing view");
        return "index";
    }
}
