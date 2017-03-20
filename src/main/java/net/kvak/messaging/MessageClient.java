package net.kvak.messaging;

/**
 * Created by korteke on 20/03/2017.
 */
public interface MessageClient {

    boolean sendFakeMessage(String message);

    boolean sendFakeMessage(String message, String url);

}
