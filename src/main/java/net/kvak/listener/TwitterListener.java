package net.kvak.listener;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.kvak.configuration.PushoverConfiguration;
import net.kvak.domain.Tweet;
import net.kvak.messaging.PushoverMessageClient;
import net.kvak.repository.TweetRepository;
import net.kvak.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twitter4j.*;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by korteke on 18/03/2017.
 */

@Slf4j
@Component
public class TwitterListener implements StatusListener {

    @NonNull
    @Value("${twitter.potus45}")
    private String potus45;

    @Value("${version}")
    private int version;


    @Autowired
    private TweetService tweetService;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private PushoverMessageClient pushoverMessageClient;

    @Autowired
    private PushoverConfiguration pushoverConfiguration;

    @Override
    public void onStatus(Status status) {
        User user = status.getUser();

        log.debug("StatusId: {}",status.getId());
        log.debug("Text: {}",status.getText());
        log.debug("UserId: {}",status.getUser().getId());
        if (status.getSource() != null) {
            log.debug("Source: {}",status.getSource());
        }
        if (status.getGeoLocation() != null) {
            log.debug("Location: {}",status.getGeoLocation());
        }


        if (potus45.equals(String.valueOf(user.getId()))) {
            log.info("All mighty POTUS tweeted: {}",status.getText());

            Tweet tweet = new Tweet(user.getId(),status.getId(),status.getText(), version);

            if(status.getURLEntities().length != 0) {
                log.debug("Tweet contains URLs");
                for (URLEntity x : status.getURLEntities()) {
                    tweet.setUrl(x.getURL());
                }
            }
            if (status.getMediaEntities().length != 0) {
                log.debug("Tweet contains media");
                for (MediaEntity x : status.getMediaEntities()) {
                    tweet.setMedia(x.getMediaURL());
                }
            }
            tweetRepository.save(tweet);
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        log.info("Potus did a BAD thing. Bad hombre, SAD!");

        Long deleted = statusDeletionNotice.getStatusId();
        Tweet tweet = tweetRepository.findByTweetId(deleted);
        log.debug("Deleted text: {}",tweet.getTweetText());

        log.debug("Retweet tweetId: {}",tweet.getTweetId());
        Long statusId = tweetService.potusTweet(tweet);

        log.debug("Replying to tweet with details");
        Date created = new ObjectId(tweet.getId()).getDate();
        log.debug("Original tweet created: {}",created.toString());

        tweetService.replyWithDetails(statusId,created);

        log.debug("Reply sent to tweetId [{}] with details [{}]",statusId,created.toString());

        log.debug("Pushover enabled: {}", pushoverConfiguration.getEnabled());
        if (Boolean.valueOf(pushoverConfiguration.getEnabled())) {
            if (tweet.getUrl() == null) {
                log.debug("Messaging without url");
                pushoverMessageClient.sendFakeMessage(tweet.getTweetText());
            } else {
                log.debug("Messaging with url");
                pushoverMessageClient.sendFakeMessage(tweet.getTweetText(),tweet.getUrl());
            }
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

        log.error("VERY SAD!");
        ex.printStackTrace();
    }
}