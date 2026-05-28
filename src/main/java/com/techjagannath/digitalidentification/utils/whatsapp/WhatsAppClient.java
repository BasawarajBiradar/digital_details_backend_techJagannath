package com.techjagannath.digitalidentification.utils.whatsapp;

import com.techjagannath.digitalidentification.config.WhatsAppConfig;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WhatsAppClient {

//    private final WhatsAppConfig config;
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    public WhatsAppClient(WhatsAppConfig config) {
//        this.config = config;
//    }
//
//    public String sendMessage(String payload) {
//
//        String url = config.getApiUrl()
//                + "/" + config.getPhoneNumberId()
//                + "/messages";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(config.getToken());
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> request = new HttpEntity<>(payload, headers);
//
//        return restTemplate.postForObject(url, request, String.class);
//    }
}
