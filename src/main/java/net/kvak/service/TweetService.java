package net.kvak.service;

import lombok.extern.slf4j.Slf4j;
import net.kvak.domain.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by korteke on 18/03/2017.
 */
@Slf4j
@Service
public class TweetService {

    @Autowired
    private Twitter twitter;

    // TODO: Handle media content
    public Long potusTweet(Tweet tweet) {
        Long id = 0L;
        try {
            org.springframework.social.twitter.api.Tweet tweetStatus = twitter.timelineOperations().updateStatus(tweet.getTweetText());
            id = tweetStatus.getId();
            log.debug("Sent GREAT tweet {} with Id: {}", tweet.getTweetText(),tweetStatus.getId());
            return id;
        } catch (RuntimeException ex) {
            log.error("Something went wrong :( SAD! {} {}",tweet.getTweetText(), ex);
        }
        return id;
    }

    public void replyWithDetails(Long tweetId, Date date) {
        StringBuilder sb = new StringBuilder();
        sb.append("Original tweet posted at: ");
        sb.append(date.toString());
        sb.append(".");
        TweetData tweetData = new TweetData(sb.toString());
        tweetData.inReplyToStatus(tweetId);

        try {
            twitter.timelineOperations().updateStatus(tweetData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}