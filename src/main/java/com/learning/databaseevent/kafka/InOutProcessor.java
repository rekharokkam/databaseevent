package com.learning.databaseevent.kafka;

import com.learning.spring.kafka.avro.Customer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

//@Configuration
@Slf4j
public class InOutProcessor {

//    @Bean
    public Function <KStream<String, Customer>, KStream<String, Customer>> process () {
              return new SimpleInOutProcessor ();
    }

    class SimpleInOutProcessor implements Function <KStream<String, Customer>, KStream<String, Customer>> {

        @Override
        public KStream<String, Customer> apply(KStream<String, Customer> input) {
            log.info("Inside the apply method of SimpleInOutProcessor");

//            return input.flatMap(
//                    (key, value) -> {
//                        log.info("Inside the generation of outbound Object");
//                        List<KeyValue<String, Customer>> result = new LinkedList<>();
//
//                        Customer outboundCustomer = new Customer();
//                        outboundCustomer.setAge(value.getAge() + 10);
//                        outboundCustomer.setAutomatedEmail(value.getAutomatedEmail());
//                        outboundCustomer.setHeight(value.getHeight() + 2);
//                        outboundCustomer.setWeight(value.getWeight() + 50);
//                        outboundCustomer.setFirstName(value.getFirstName().toUpperCase());
//                        outboundCustomer.setLastName(value.getLastName().toLowerCase());
//
//                        log.info("Before Adding the key to the result : {}", "test-key".toUpperCase());
//                        log.info("Before adding the customer payload : {}", outboundCustomer);
//
//                        result.add(KeyValue.pair("test-key".toUpperCase(), outboundCustomer) );
//                        return result;
//                    }
//            );

//            return input.flatMap(new MyKeyValueMapper());
            
              return input.flatMapValues(new MyValueMapper());
        }
    }

    class MyValueMapper implements ValueMapper <Customer, List<Customer>> {
        @Override
        public List<Customer> apply(Customer value) {
            log.info("Inside the apply method of MyValueMapper");
            Customer outboundCustomer = new Customer();
            outboundCustomer.setAge(value.getAge() + 10);
            outboundCustomer.setAutomatedEmail(value.getAutomatedEmail());
            outboundCustomer.setHeight(value.getHeight() + 2);
            outboundCustomer.setWeight(value.getWeight() + 50);
            outboundCustomer.setFirstName(value.getFirstName().toUpperCase());
            outboundCustomer.setLastName(value.getLastName().toLowerCase());

            List<Customer> result = new LinkedList<>();
            result.add(outboundCustomer);
            log.info("outboundcustomer added : {}", outboundCustomer);
            return result;
        }
    }

    class MyKeyValueMapper implements KeyValueMapper<String, Customer, List<KeyValue<String, Customer>>> {

        @Override
        public List<KeyValue<String, Customer>> apply(String key, Customer value) {

            Customer outboundCustomer = new Customer();
            outboundCustomer.setAge(value.getAge() + 10);
            outboundCustomer.setAutomatedEmail(value.getAutomatedEmail());
            outboundCustomer.setHeight(value.getHeight() + 2);
            outboundCustomer.setWeight(value.getWeight() + 50);
            outboundCustomer.setFirstName(value.getFirstName().toUpperCase());
            outboundCustomer.setLastName(value.getLastName().toLowerCase());

            List<KeyValue<String, Customer>> result = new LinkedList<>();
            result.add(KeyValue.pair("test-key".toUpperCase(), outboundCustomer) );
            return result;
        }
    }
}
