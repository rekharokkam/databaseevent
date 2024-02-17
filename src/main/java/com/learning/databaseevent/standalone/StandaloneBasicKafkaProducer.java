package com.learning.databaseevent.standalone;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import com.learning.spring.kafka.avro.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

@Slf4j
public class StandaloneBasicKafkaProducer {
    private static final String BOOTSTRAP_SERVER = "localhost:9092";
    private static final String CUSTOMER_AVRO_TOPIC_NAME = "customer-inbound-topic";
    private static final String MESSAGE_KEY = "customer_v2";

    public static void main(String[] args) {

        ch.qos.logback.classic.Logger parentLogger =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        parentLogger.setLevel(ch.qos.logback.classic.Level.DEBUG);
        System.out.println("default logging level : " + parentLogger.isDebugEnabled());

        Properties kafkaAvroProducerProperties = new Properties();

        kafkaAvroProducerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        kafkaAvroProducerProperties.setProperty (ProducerConfig.ACKS_CONFIG, "all");
        kafkaAvroProducerProperties.setProperty (ProducerConfig.RETRIES_CONFIG, "10"); //need not be set
        kafkaAvroProducerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaAvroProducerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        kafkaAvroProducerProperties.setProperty("auto.register.schemas", "false");
        kafkaAvroProducerProperties.setProperty("schema.registry.url", "http://localhost:8081");

        KafkaProducer<String, Customer> customerV1KafkaProducer = new KafkaProducer<String, Customer>(kafkaAvroProducerProperties);

        Customer customerV2 = Customer.newBuilder()
                .setFirstName("gulab1")
                .setAge(26)
//                .setAutomatedEmail(false)
                .setHeight(100.5f)
                .setLastName("Jamoon1")
                .setWeight(1110.6f)
//                .setPhoneNumber("123-456-7890")
//                .setEmail("gulab.jamun@example.com")
                .build();

        ProducerRecord<String, Customer> customerV1ProducerRecord =
                new ProducerRecord<String, Customer>(CUSTOMER_AVRO_TOPIC_NAME, MESSAGE_KEY, customerV2);

        customerV1KafkaProducer.send(customerV1ProducerRecord, new Callback() {

            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    log.info("send is successful");
                    log.info(metadata.topic());
                } else {
                    log.error("There was an exception while sending message to the customer Topic", exception);
                }
            }
        });

        customerV1KafkaProducer.flush();
        customerV1KafkaProducer.close();
    }
}
