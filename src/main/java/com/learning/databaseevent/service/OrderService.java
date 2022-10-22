package com.learning.databaseevent.service;

import com.learning.databaseevent.dataobject.Order;

public interface OrderService {
        void updateOrder (Order order);
        Order getOrder (String orderId);
        Order createOrder (Order order);
}
