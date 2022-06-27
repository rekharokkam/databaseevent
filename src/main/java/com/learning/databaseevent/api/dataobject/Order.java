package com.learning.databaseevent.api.dataobject;

import lombok.Data;

@Data
public class Order {
    private String orderId;
    private String orderStatus;
    private String orderType;
}
