package net.kvak.messaging;

import lombok.extern.slf4j.Slf4j;
import net.kvak.configuration.PushoverConfiguration;
import net.pushover.client.PushoverClient;
import net.pushover.client.PushoverException;
import net.pushover.client.PushoverMessage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by korteke on 20/03/2017.
 */

@Slf4j
public class PushoverMessageClient implements MessageClient {

    @Autowired
    private PushoverClient pushoverClient;

    @Autowired
    private PushoverConfiguration pushoverConfiguration;

    @Override
    public boolean sendFakeMessage(String message) {

        try {
            pushoverClient.pushMessage(PushoverMessage.builderWithApiToken(pushoverConfiguration.getApiToken())
                    .setUserId(pushoverConfiguration.getUserToken())
                    .setTitle(pushoverConfiguration.getHombre())
                    .setMessage(message)
                    .build());
        } catch (PushoverException e) {
            log.error("Can't send dishonest pushover message, SAD! ");
            return false;
        }

        log.debug("sendFakeMessage");
        return true;
    }

    @Override
    public boolean sendFakeMessage(String message, String url) {

        try {
            pushoverClient.pushMessage(PushoverMessage.builderWithApiToken(pushoverConfiguration.getApiToken())
                    .setUserId(pushoverConfiguration.getUserToken())
                    .setMessage(message)
                    .setTitle(pushoverConfiguration.getHombre())
                    .setUrl(url)
                    .build());
        } catch (PushoverException e) {
            log.error("Can't send dishonest pushover message, SAD!");
            return false;
        }

        log.debug("sendFakeMessage with url");
        return  true;
    }

}
