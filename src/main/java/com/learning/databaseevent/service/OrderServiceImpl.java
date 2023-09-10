package com.learning.databaseevent.service;

import com.learning.databaseevent.dataobject.Order;
import com.learning.databaseevent.events.Event;
import com.learning.databaseevent.events.EventPublisher;
import com.learning.databaseevent.events.EventSubscriber;
import com.learning.databaseevent.repository.OrderRepo;
import com.learning.databaseevent.repository.entity.OrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import jakarta.transaction.Transactional;

import static com.learning.databaseevent.utils.DatabaseEventHelper.uniqueIdentifier;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService, EventSubscriber {
    private OrderRepo orderRepo;
    private EventPublisher eventPublisher;
    private ProductService productService;

    public OrderServiceImpl (OrderRepo orderRepo, EventPublisher eventPublisher, ProductService productService){
        this.orderRepo = orderRepo;
        this.eventPublisher = eventPublisher;
        this.productService = productService;

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
        order.setOrderNumber(currentOrderEntity.getOrderNumber());
        order.setPrId(Long.toString(currentOrderEntity.getProduct().getPrId()));

        return order;
    }

    @Override
    public Order createOrder(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderStatus(order.getOrderStatus());
        orderEntity.setOrderType(order.getOrderType());
        orderEntity.setOrderNumber(uniqueIdentifier());
        orderEntity.setProduct(productService.getProduct(Long.valueOf(order.getPrId())));

        log.info("Order to be saved : {}", orderEntity);
        OrderEntity savedOrderEntity = orderRepo.save(orderEntity);
        order.setOrderId(savedOrderEntity.getOrderId());
        order.setOrderNumber(savedOrderEntity.getOrderNumber());
        return order;
    }

    @Override
    public void processEvent(Event orderSavedEvent) {
        log.info("I am inside the processEvent method");
    }
}
