package com.example.springbootapi.loggers;

import com.example.springbootapi.models.EventLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MessageLogger {
    @Autowired
    private KafkaTemplate<String, EventLog> kafkaTemplate;

    @Value(value = "${kafka.message.topic.name}")
    private String topicName;

    public void info(String message) {
        EventLog eventLog = new EventLog();
        eventLog.setLevel("INFO");
        eventLog.setMessage(message);
        eventLog.setDate(LocalDateTime.now());
        kafkaTemplate.send(topicName, eventLog);
    }

    public void error(String message) {
        EventLog eventLog = new EventLog();
        eventLog.setLevel("ERROR");
        eventLog.setMessage(message);
        eventLog.setDate(LocalDateTime.now());
        kafkaTemplate.send(topicName, eventLog);
    }
}
