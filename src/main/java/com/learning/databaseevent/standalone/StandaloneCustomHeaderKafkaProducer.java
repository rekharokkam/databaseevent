package com.learning.databaseevent.standalone;

import com.learning.spring.kafka.avro.Customer;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

@Slf4j
public class StandaloneCustomHeaderKafkaProducer {
    private static final String BOOTSTRAP_SERVER = "localhost:9092";
    private static final String CUSTOMER_AVRO_TOPIC_NAME = "customer-inbound-topic";
    private static final String MESSAGE_KEY = "customer_v2";

    public static void main(String[] args) {

        ch.qos.logback.classic.Logger parentLogger =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        parentLogger.setLevel(ch.qos.logback.classic.Level.DEBUG);
        System.out.println("default logging level : " + parentLogger.isDebugEnabled());

        KafkaProducer<String, Customer> customerV1KafkaProducer = getStringCustomerKafkaProducer();

        Customer customerV2 = Customer.newBuilder()
                .setFirstName("gulab2")
                .setAge(26)
//                .setAutomatedEmail(false)
                .setHeight(100.5f)
                .setLastName("Jamoon2")
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

    @NotNull
    private static KafkaProducer<String, Customer> getStringCustomerKafkaProducer() {
        Properties kafkaAvroProducerProperties = new Properties();

        kafkaAvroProducerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        kafkaAvroProducerProperties.setProperty (ProducerConfig.ACKS_CONFIG, "all");
        kafkaAvroProducerProperties.setProperty (ProducerConfig.RETRIES_CONFIG, "10"); //need not be set
        kafkaAvroProducerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaAvroProducerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        kafkaAvroProducerProperties.setProperty("auto.register.schemas", "false");
        kafkaAvroProducerProperties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4");
        kafkaAvroProducerProperties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        kafkaAvroProducerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "databaseevent_application");
        kafkaAvroProducerProperties.setProperty(ProducerConfig.TRANSACTIONAL_ID_CONFIG, UUID.randomUUID().toString());

        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-api-key", "123456");

        Map<String, ?> originals = null;
        CachedSchemaRegistryClient srClient =
                new CachedSchemaRegistryClient("http://localhost:8081", 10,  originals, customHeaders);
        KafkaProducer<String, Customer> customerV1KafkaProducer =
                new KafkaProducer(kafkaAvroProducerProperties, new StringSerializer(), new KafkaAvroSerializer(srClient));
        return customerV1KafkaProducer;
    }
}
