package com.learning.databaseevent.controller;

import com.learning.databaseevent.dataobject.Order;
import com.learning.databaseevent.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping (path = "/orders")
public class OrdersController {
    private OrderService orderService;

    public OrdersController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping (path = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getOrder (@PathVariable ("orderId") String orderId) {

        log.info("OrderId to search : {}", orderId);
        Order order = orderService.getOrder(orderId);
        log.info("Order Found : " + order);
        return order;
    }

    @PutMapping (path = "/{orderId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateOrder (@PathVariable ("orderId") String orderId,
                                  @RequestBody Order order) {
        order.setOrderId(orderId);
        log.info("Before Update : " + orderId);
        orderService.updateOrder(order);
        log.info("Order updated Successfully : " );
    }

    @PostMapping (consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus (HttpStatus.CREATED)
    public ResponseEntity<Order> createOrder (@RequestBody Order order){
        log.info ("Order to be added : " + order);
        Order newOrder = orderService.createOrder(order);
        log.info("Order After Created : " + newOrder);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(newOrder.getOrderId())
                .toUri();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.LOCATION, location.toString());
        headers.add("Content-Type", "application/json");
        ResponseEntity<Order> response = new ResponseEntity<>(newOrder, headers, HttpStatus.CREATED);
        return response;
    }

    @ExceptionHandler (value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private void handleException (Exception ex){
        log.error("Error occurred while invoking some operation", ex);
    }
}
