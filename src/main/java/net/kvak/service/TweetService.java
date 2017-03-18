package net.kvak.service;

import lombok.extern.slf4j.Slf4j;
import net.kvak.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

/**
 * Created by korteke on 18/03/2017.
 */
@Slf4j
@Service
public class TweetService {

    @Autowired
    private Twitter twitter;

    public void potusTweet(Tweet tweet) {
        try {
            twitter.timelineOperations().updateStatus(tweet.getTweetText());
            log.debug("Sent new tweet {}", tweet.getTweetText());
        } catch (RuntimeException ex) {
            log.error("Something went wrong :( SAD! {} {}",tweet.getTweetText(), ex);
        }
    }
}