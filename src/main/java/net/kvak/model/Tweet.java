package net.kvak.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Service;

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

    @Getter
    @Setter
    @Field("version")
    public int version;

    @Getter
    @Setter
    @Field("url")
    public String url;

    @Getter
    @Setter
    @Field("media")
    public String media;


    public Tweet() {

    }

    public Tweet(Long userid, Long tweetId, String tweetText, int version) {
        this.tweetText = tweetText;
        this.userId = userid;
        this.tweetId = tweetId;
        this.version = version;
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
