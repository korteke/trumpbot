package net.kvak.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;

import javax.annotation.PostConstruct;

/**
 * Created by korteke on 18/03/2017.
 */

@Slf4j
@Service
public class TwitterService {

    @Autowired
    private TwitterStream twitterStream;

    @Autowired
    private StatusListener statusListener;

    @NonNull
    @Value("${twitter.potus45}")
    private String potus45;

    @PostConstruct
    public void jobsjobsjobs() {

        long [] potusId = {returnTrumpId(potus45)};
        FilterQuery tweetFilterQuery = new FilterQuery();
        tweetFilterQuery.follow(potusId);

        twitterStream.addListener(statusListener);
        twitterStream.filter(tweetFilterQuery);
    }

    private Long returnTrumpId(String sad) {
        log.debug("Sad twitterId: {}",sad);
        return Long.valueOf(sad);
    }
}
