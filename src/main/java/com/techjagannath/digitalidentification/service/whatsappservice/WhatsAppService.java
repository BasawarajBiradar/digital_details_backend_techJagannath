package com.techjagannath.digitalidentification.service.whatsappservice;

import com.techjagannath.digitalidentification.utils.whatsapp.WhatsAppClient;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

//    private final WhatsAppClient client;
//
//    public WhatsAppService(WhatsAppClient client) {
//        this.client = client;
//    }
//
//    public String sendEntryAlert(String to, String name, String time) {
//
//        String payload = buildTemplatePayload(
//                to,
//                "attendance_alert",
//                name,
//                time
//        );
//
//        return client.sendMessage(payload);
//    }
//
//    public String sendExitAlert(String to, String name, String time) {
//
//        String payload = buildTemplatePayload(
//                to,
//                "exit_alert",
//                name,
//                time
//        );
//
//        return client.sendMessage(payload);
//    }
//
//    private String buildTemplatePayload(String to, String template,
//                                        String param1, String param2) {
//
//        return "{"
//                + "\"messaging_product\":\"whatsapp\","
//                + "\"to\":\"" + to + "\","
//                + "\"type\":\"template\","
//                + "\"template\":{"
//                + "\"name\":\"" + template + "\","
//                + "\"language\":{\"code\":\"en\"},"
//                + "\"components\":[{"
//                + "\"type\":\"body\","
//                + "\"parameters\":["
//                + "{\"type\":\"text\",\"text\":\"" + param1 + "\"},"
//                + "{\"type\":\"text\",\"text\":\"" + param2 + "\"}"
//                + "]"
//                + "}]"
//                + "}"
//                + "}";
//    }
}