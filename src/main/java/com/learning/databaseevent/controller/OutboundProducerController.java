package com.learning.databaseevent.controller;

import com.learning.databaseevent.dataobject.Order;
import com.learning.spring.kafka.avro.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MimeType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.function.Supplier;

@Slf4j
@RestController
@RequestMapping(path = "/customers")
public class OutboundProducerController {

    @Autowired
    private StreamBridge streamBridge;

//    public OutboundProducerController (StreamBridge streamBridge){
//        this.streamBridge = streamBridge;
//    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> publishOrder (){
        log.info ("Inside the OutboundProducerController POST method : ");
        Customer outboundCustomer = getOutboundCustomer();
        log.info("customer fetched : {}", outboundCustomer.getClass().getName());
        log.info("Customer classloader name: {} :: this classloader : {} :: StreamBridge classloader : {}",
                outboundCustomer.getClass().getClassLoader().getName(),
                this.getClass().getClassLoader().getName(),
                streamBridge.getClass().getClassLoader().getName());
//        StreamBridge streamBridge = context.getBean(StreamBridge.class);
        streamBridge.send("output", outboundCustomer);

        streamBridge.send("output", MessageBuilder.withPayload(outboundCustomer)
                .build(), new MimeType("application", "*+avro"));

        return new ResponseEntity<>("Ok", HttpStatus.CREATED);
    }

    public Customer getOutboundCustomer () {
        log.info("Inside the method to generate Customer class");
        Customer outboundCustomer = new Customer();
        outboundCustomer.setAge(20);
        outboundCustomer.setAutomatedEmail(false);
        outboundCustomer.setHeight(2);
        outboundCustomer.setWeight(50);
        outboundCustomer.setFirstName("Jane553".toUpperCase());
        outboundCustomer.setLastName("Doe553".toLowerCase());

        log.info("Customer class returned : {}", outboundCustomer);
        return outboundCustomer;
    }
}
