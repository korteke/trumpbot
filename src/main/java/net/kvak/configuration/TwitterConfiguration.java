package net.kvak.configuration;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.kvak.listener.TwitterListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by korteke on 18/03/2017.
 */

@Slf4j
@Configuration
public class TwitterConfiguration {

    @NonNull
    @Value("${twitter.consumerKey}")
    private String consumerKey;

    @NonNull
    @Value("${twitter.consumerSecret}")
    private String consumerSecret;

    @NonNull
    @Value("${twitter.accessToken}")
    private String accessToken;

    @NonNull
    @Value("${twitter.accessTokenSecret}")
    private String accessTokenSecret;

    @Bean
    public TwitterStream twitterStream() {

        TwitterStreamFactory tf = new TwitterStreamFactory(createConfigurationBuilder().build());
        TwitterStream twitterStream = tf.getInstance();

        log.debug("twitterStream");
        return twitterStream;
    }

    @Bean
    public StatusListener statusListener() {

        return new TwitterListener();
    }

    private ConfigurationBuilder createConfigurationBuilder() {

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret)
                .setDebugEnabled(true);

        log.debug("createConfigurationBuilder");
        return configurationBuilder;
    }

    @Bean
    public Twitter twitter() {
        TwitterTemplate twitterTemplate = new TwitterTemplate(consumerKey,consumerSecret,accessToken,accessTokenSecret);

        log.debug("twitterTemplate");
        return twitterTemplate;
    }


}
