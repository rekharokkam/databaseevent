package com.learning.databaseevent.service;

import com.learning.databaseevent.api.dataobject.Order;
import com.learning.databaseevent.events.Event;
import com.learning.databaseevent.events.EventPublisher;
import com.learning.databaseevent.events.EventSubscriber;
import com.learning.databaseevent.repository.OrderRepo;
import com.learning.databaseevent.repository.entity.OrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService, EventSubscriber {
    private OrderRepo orderRepo;
    private EventPublisher eventPublisher;

    @Autowired
    public OrderServiceImpl (OrderRepo orderRepo, EventPublisher eventPublisher){
        this.orderRepo = orderRepo;
        this.eventPublisher = eventPublisher;

        eventPublisher.addSubscriber(this);
    }

    @Override
    public void updateOrder(Order order) {
        OrderEntity currentOrderEntity = orderRepo.findById(order.getOrderId()).get();
        currentOrderEntity.setOrderStatus(order.getOrderStatus());
        currentOrderEntity.setOrderType(order.getOrderType());
        OrderEntity updatedOrderEntity = orderRepo.save(currentOrderEntity);
    }

    @Override
    public Order getOrder(String orderId) {
        Assert.notNull(orderId, "orderId cannot be null");
        OrderEntity currentOrderEntity = orderRepo.findById(orderId).get();
        Order order = new Order();
        order.setOrderId(currentOrderEntity.getOrderId());
        order.setOrderStatus(currentOrderEntity.getOrderStatus());
        order.setOrderType(currentOrderEntity.getOrderType());

        return order;
    }

    @Override
    public Order createOrder(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderStatus(order.getOrderStatus());
        orderEntity.setOrderType(order.getOrderType());

        OrderEntity savedOrderEntity = orderRepo.save(orderEntity);
        order.setOrderId(savedOrderEntity.getOrderId());
        return order;
    }

    @Override
    public void processEvent(Event orderSavedEvent) {
        log.info("I am inside the processEvent method");
    }
}
