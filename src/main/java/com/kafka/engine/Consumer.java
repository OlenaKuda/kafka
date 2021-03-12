package com.kafka.engine;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    //example 1
    @KafkaListener(topics = "${my.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id:}")
    public void listen(ConsumerRecord<String, String> kafkaMessage) {
        logger.info(String.format("Received data     = %s", kafkaMessage.value()));
    }

    //example 2
    @KafkaListener(topics = "users", groupId = "group_id")
    public void consume(String message) {
        logger.info(String.format("#### -> Consumed message -> %s", message));
    }
}
