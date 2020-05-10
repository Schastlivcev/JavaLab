package ru.kpfu.itis.rodsher.jlmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
@Profile("stomp")
public class StompJsonMessageConverter implements MessageConverter {
    private ObjectMapper objectMapper;
    private MappingJackson2MessageConverter jackson2MessageConverter;

    public StompJsonMessageConverter(ObjectMapper objectMapper, MappingJackson2MessageConverter jackson2MessageConverter) {
        this.objectMapper = objectMapper;
        this.jackson2MessageConverter = jackson2MessageConverter;
    }

    @Override
    public Object fromMessage(Message<?> message, Class<?> aClass) {
        byte[] payloadBytes = (byte[]) message.getPayload();
        String payloadJson = new String(payloadBytes);
        try {
            JsonNode payload = objectMapper.readTree(payloadJson);
            return payload;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Unacceptable payload format.", e);
        }
    }

    @Override
    public Message<?> toMessage(Object o, MessageHeaders messageHeaders) {
        return jackson2MessageConverter.toMessage(o, messageHeaders);
    }
}
