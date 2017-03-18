package net.kvak.model;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.kvak.repository.TweetRepository;
import net.kvak.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twitter4j.*;

/**
 * Created by korteke on 18/03/2017.
 */

@Slf4j
@Component
public class TwitterListener implements StatusListener {

    @NonNull
    @Value("${twitter.potus45}")
    private String potus45;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public void onStatus(Status status) {
        User user = status.getUser();

        log.debug("StatusId: {}",status.getId());
        log.debug("Text: {}",status.getText());
        log.debug("UserId: {}",status.getUser().getId());

        if (potus45.equals(String.valueOf(user.getId()))) {
            log.info("All mighty POTUS tweeted: {}",status.getText());
            tweetRepository.save(new Tweet(user.getId(),status.getId(),status.getText()));
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        log.info("Potus did a BAD thing. Bad hombre, SAD!");

        Long deleted = statusDeletionNotice.getStatusId();
        Tweet tweet = tweetRepository.findByTweetId(deleted);
        log.debug("Deleted text: {}",tweet.getTweetText());

        if (tweet != null) {
            log.debug("Retweet tweetId: {}",tweet.getTweetId());
            tweetService.potusTweet(tweet);
        }
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        log.info("Limitation notice");
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
        log.info("scrub_geo notice");
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        log.info("Stall warning notice");
    }

    @Override
    public void onException(Exception ex) {

        ex.printStackTrace();
    }
}