package net.kvak.repository;

import net.kvak.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by korteke on 18/03/2017.
 */

public interface TweetRepository extends MongoRepository<Tweet, String> {

    Tweet findByTweetId(Long tweetId);

}
