package com.learning.databaseevent.kafka;

import com.learning.spring.kafka.avro.Customer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

//@Configuration
@Slf4j
public class InboundConsumer {

//    @Bean
    public Consumer<KStream<String, Customer>> inbound() {
        log.info("\n\nInside the topic consumer method\n\n");
//        return input -> input.foreach( (key, value) -> {
//            log.info("Key : {} :: Value : {}", key, value);
//        });
        return new SimpleConsumer();
    }

    class SimpleConsumer implements Consumer<KStream<String, Customer>> {

        @Override
        public void accept (KStream<String, Customer> input) {
            input.foreach((key, value) -> {
                log.info("Key : {} :: Value : {}", key, value);
            } );
        }
    }

//    @Bean
//    public Consumer<Customer> inbound() {
//        System.out.println("\n\nInside the topic consumer method\n\n");
//          return input -> {
//              log.info("Inside the message processing");
//              Acknowledgment acknowledgment = input.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
//              String offset = msg.getHeaders().get(KafkaHeaders.OFFSET, String.class);
//              String topic = msg.getHeaders().get(KafkaHeaders.TOPIC, String.class);
//              String key = msg.getHeaders().get(KafkaHeaders.KEY, String.class);
//              log.info("acknowledge: {} :: offset : {} :: topic : {} :: key : {}" + acknowledgment + " :: offset : " + offset +
//                      " :: topic : " + topic + " :: key : " + key);
//              log.info("Payload : {}", msg.getPayload());
//          };
//        } );
//    }

}
