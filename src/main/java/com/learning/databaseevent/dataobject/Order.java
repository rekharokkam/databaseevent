package com.learning.databaseevent.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderId;
    private String orderStatus;
    private String orderType;
    private String prId;
    private String orderNumber;
}
