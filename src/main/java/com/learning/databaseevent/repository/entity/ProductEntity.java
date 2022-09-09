package com.learning.databaseevent.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @Column (name = "prId", length = 225)
    private String prId;

    @Column (name = "name", length = 225, nullable = false)
    private String name;

    @Column(name = "tcin", nullable = false, length = 255)
    private String tcin;

    @Column(name = "dpci", nullable = false, length = 255)
    private String dpci;

    @Column(name = "upc", nullable = false, length = 255)
    private String upc;

    @Column(name = "model", nullable = false, length = 255)
    private String model;
}
