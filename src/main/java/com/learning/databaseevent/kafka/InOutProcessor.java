package com.learning.databaseevent.kafka;

import com.learning.spring.kafka.avro.Customer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

@Configuration
@Slf4j
public class InOutProcessor {

    @Bean
    public Function <KStream<String, Customer>, KStream<String, Customer>> process () {
              return new SimpleInOutProcessor ();
    }

    class SimpleInOutProcessor implements Function <KStream<String, Customer>, KStream<String, Customer>> {

        @Override
        public KStream<String, Customer> apply(KStream<String, Customer> input) {

            return input.flatMap(
                    (key, value) -> {
                        System.out.println("Coming here");
                        List<KeyValue<String, Customer>> result = new LinkedList<>();
                        Customer outboundCustomer = new Customer();
                        outboundCustomer.setAge(value.getAge() + 10);
                        outboundCustomer.setAutomatedEmail(value.getAutomatedEmail());
                        outboundCustomer.setHeight(value.getHeight() + 2);
                        outboundCustomer.setWeight(value.getWeight() + 50);
                        outboundCustomer.setFirstName(value.getFirstName().toUpperCase());
                        outboundCustomer.setLastName(value.getLastName().toLowerCase());

                        log.info("Before Adding the key to the result : {}", "test-key".toUpperCase());
                        log.info("Before adding the customer payload : {}", outboundCustomer);

                        result.add(KeyValue.pair("test-key".toUpperCase(), outboundCustomer) );
                        return result;
                    }
            );


//            input.foreach((key, value) -> {
//                log.info("Inbound Key : {}", key);
//
//                Customer inboundCustomer = value;
//                log.info("Inbound Payload : {}", inboundCustomer);
//
//                KStream<String, Customer> something =  input.flatMapValues (eachValue -> {
//                    log.info("Inside the payload processor: {}", eachValue);
//                    return Arrays.asList(eachValue);
//                }).map(
//                     (eachKey, eachValue) -> {
//                         System.out.println("Coming here");
//                         return new KeyValue<>(key.toUpperCase(), value);
//                     } ) ;

//                KStream<String, Customer> outboundCustomer = input.flatMap(
//                        (eachKey, eachValue) -> {
//                            System.out.println("Coming here");
//                            Customer outboundCustomer1 = new Customer();
//                            outboundCustomer1.setAge(eachValue.getAge() + 10);
//                            outboundCustomer1.setAutomatedEmail(eachValue.getAutomatedEmail());
//                            outboundCustomer1.setHeight(eachValue.getHeight() + 2);
//                            outboundCustomer1.setWeight(eachValue.getWeight() + 50);
//                            outboundCustomer1.setFirstName(eachValue.getFirstName().toUpperCase());
//                            outboundCustomer1.setLastName(eachValue.getLastName().toLowerCase());
//
//                            log.info("Before Adding the key to the result : {}", eachKey.toUpperCase());
//                            log.info("Before adding the customer payload : {}", outboundCustomer1);
//
//                            List<KeyValue <String, Customer>> result = new LinkedList<>();
//                            result.add(KeyValue.pair(eachKey.toUpperCase(), outboundCustomer1));
//
//                            return result;
//                        }
//                );
//            });
        }
    }

    class MyKeyValueMapper implements KeyValueMapper<String, Customer, Customer> {

        @Override
        public Customer apply(String key, Customer value) {
            Customer outboundCustomer = new Customer();
            outboundCustomer.setAge(value.getAge() + 10);
            outboundCustomer.setAutomatedEmail(value.getAutomatedEmail());
            outboundCustomer.setHeight(value.getHeight() + 2);
            outboundCustomer.setWeight(value.getWeight() + 50);
            outboundCustomer.setFirstName(value.getFirstName().toUpperCase());
            outboundCustomer.setLastName(value.getLastName().toLowerCase());
            return new Customer();
        }
    }
}
