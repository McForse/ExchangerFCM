package com.shotball.exchangerfcm.firebase;

import com.google.firebase.messaging.*;
import com.shotball.exchangerfcm.model.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Service
public class FCMService {

    private Logger logger = LoggerFactory.getLogger(FCMService.class);

    public void sendMessageToTopic(PushNotificationRequest request) throws InterruptedException, ExecutionException {
        Message message = getMessageToTopic(request);
        String response = sendAndGetResponse(message);
        logger.info("Sent message to topic. Topic: " + request.getTopic() + ", " + response);
    }

    public void sendMessageToToken(PushNotificationRequest request) throws InterruptedException, ExecutionException {
        Message message = getMessageToToken(request);
        String response = sendAndGetResponse(message);
        logger.info("Sent message to token. Device token: " + request.getToken() + ", " + response);
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private Message getMessageToToken(PushNotificationRequest request) {
        return getMessageBuilder(request)
                .setToken(request.getToken())
                .build();
    }

    private Message getMessageToTopic(PushNotificationRequest request) {
        return getMessageBuilder(request)
                .setTopic(request.getTopic())
                .build();
    }

    private Message.Builder getMessageBuilder(PushNotificationRequest request) {
        String topic = request.getTopic();

        AndroidConfig androidConfig = AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic).build();

        ApnsConfig apnsConfig = ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setCategory(topic)
                        .setThreadId(topic)
                        .build())
                .build();

        return Message.builder()
                .setApnsConfig(apnsConfig)
                .setAndroidConfig(androidConfig)
                .putData("title", request.getTitle())
                .putData("message", request.getMessage())
                .putData("imageUrl", request.getImageUrl())
                .putData("username", request.getUsername())
                .putData("from_uid", request.getFrom());
    }

}
