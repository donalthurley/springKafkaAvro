package com.springkafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataProducer implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataProducer.class);
    private final KafkaTemplate<String, Person> kafka;

    @Value("${com.springkafka.topic}")
    private String topic;

    public DataProducer(
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") KafkaTemplate<String, Person> kafka) {
        this.kafka = kafka;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String key = String.valueOf(new Random().nextLong());

        Person person = Person.newBuilder()
                .setFirstName("John")
                .setLastName("Doe")
                .build();

        LOGGER.info("producing {}, {}", key, person);
        kafka.send(topic, key, person);
    }
}
