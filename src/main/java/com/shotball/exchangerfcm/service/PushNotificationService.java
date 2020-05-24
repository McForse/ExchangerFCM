package com.shotball.exchangerfcm.service;

import com.shotball.exchangerfcm.firebase.FCMService;
import com.shotball.exchangerfcm.model.PushNotificationRequest;
import com.shotball.exchangerfcm.model.PushNotificationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PushNotificationService {

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    private FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    public ResponseEntity<PushNotificationResponse> sendPushNotificationToTopic(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToTopic(request);
            return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.FORBIDDEN.value(), e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<PushNotificationResponse> sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
            return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.FORBIDDEN.value(), e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

}
