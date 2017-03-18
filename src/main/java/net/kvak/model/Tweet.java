package net.kvak.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by korteke on 18/03/2017.
 */

@Document(collection="sad")
public class Tweet {

    @Id
    public String id;

    @Getter
    @Setter
    @Field("userId")
    public Long userId;

    @Getter
    @Setter
    @Indexed
    @Field("tweetId")
    public Long tweetId;

    @Getter
    @Setter
    @Field("tweetText")
    public String tweetText;

    public Tweet() {

    }

    public Tweet(Long userid, Long tweetId, String tweetText) {
        this.tweetText = tweetText;
        this.userId = userid;
        this.tweetId = tweetId;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", tweetId=" + tweetId +
                ", tweetText='" + tweetText + '\'' +
                '}';
    }
}
