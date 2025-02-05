package com.desafioback.PicpaySimplificado.services;

import com.desafioback.PicpaySimplificado.domain.user.User;
import com.desafioback.PicpaySimplificado.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    @Async
    public CompletableFuture<Void> sendNotification(User user, String message) throws Exception {

        try {
            ResponseEntity<Map> notificationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v1/notify", Map.class);
            if((notificationResponse.getStatusCode() == HttpStatus.OK && "fail".equals(notificationResponse.getBody().get("status")))){
//                String email = user.getEmail();
//                NotificationDTO notificationRequest = new NotificationDTO(email, message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } return CompletableFuture.completedFuture(null);
    }
}
