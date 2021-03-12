package com.kafka.engine;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestParam;


@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Value("${my.kafka.producer.topic}")
    private String topic;

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendDataToKafka(@RequestParam String data) {


        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic, data);

        listenableFuture.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info(String.format("Sent data     = %s", result.getProducerRecord().value()));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Unable to send data to Kafka", ex);
            }
        });
    }
}
        /*logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(topic, message);
    }
}
*/
