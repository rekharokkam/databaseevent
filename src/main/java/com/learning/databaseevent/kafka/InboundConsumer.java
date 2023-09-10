package com.learning.databaseevent.kafka;

import com.learning.spring.kafka.avro.Customer;
import org.apache.kafka.streams.kstream.KStream;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

//@Configuration
public class InboundConsumer {

//    @Bean
    public Consumer<KStream<String, Customer>> inbound() {
        System.out.println("\n\nInside the topic consumer method\n\n");
//        return input -> input.foreach( (key, value) -> {
//            System.out.println("Key : " + key + " : Value : " + value);
//        });
        return new SimpleConsumer();
    }

    class SimpleConsumer implements Consumer<KStream<String, Customer>> {

        @Override
        public void accept (KStream<String, Customer> input) {
            input.foreach((key, value) -> {
                System.out.println("Key : " + key + " : Value : " + value);
            } );
        }
    }

//    @Bean
//    public Consumer<Customer> inbound() {
//        System.out.println("\n\nInside the topic consumer method\n\n");
//          return input -> {
//              System.out.println("Inside the message processing");
//              Acknowledgment acknowledgment = input.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
//              String offset = msg.getHeaders().get(KafkaHeaders.OFFSET, String.class);
//              String topic = msg.getHeaders().get(KafkaHeaders.TOPIC, String.class);
//              String key = msg.getHeaders().get(KafkaHeaders.KEY, String.class);
//              System.out.println("acknowledge: " + acknowledgment + " :: offset : " + offset +
//                      " :: topic : " + topic + " :: key : " + key);
//              System.out.println("Payload : " + msg.getPayload());
//          };
//        } );
//    }

}
