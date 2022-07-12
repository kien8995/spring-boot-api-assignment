package com.example.springbootapi.listeners;

import com.example.springbootapi.models.EventLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @Autowired
    MongoTemplate mongoTemplate;

    @KafkaListener(topics = "${kafka.message.topic.name}", groupId = "${kafka.consumer.group-id}")
    public void listenMessage(EventLog eventLog) {
//        System.out.println("Received message: " + eventLog.getLevel() + " - " + eventLog.getMessage());
        mongoTemplate.insert(eventLog);
    }
}
