package com.shotball.exchangerfcm.controller;

import com.shotball.exchangerfcm.model.PushNotificationRequest;
import com.shotball.exchangerfcm.model.PushNotificationResponse;
import com.shotball.exchangerfcm.service.PushNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PushNotificationController {

    private PushNotificationService pushNotificationService;

    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping("/notification/topic")
    public ResponseEntity<PushNotificationResponse> sendTopicNotification(@RequestBody PushNotificationRequest request) {
        return pushNotificationService.sendPushNotificationToTopic(request);
    }

    @PostMapping("/notification/token")
    public ResponseEntity<PushNotificationResponse> sendTokenNotification(@RequestBody PushNotificationRequest request) {
        return pushNotificationService.sendPushNotificationToToken(request);
    }

}
