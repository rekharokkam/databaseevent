package com.learning.databaseevent.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String prId;
    private String name;
    private String tcin;
    private String dpci;
    private String upc;
    private String model;
}
