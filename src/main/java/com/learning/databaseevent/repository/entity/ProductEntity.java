package com.learning.databaseevent.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity implements Serializable {

    @Id
    @Column (name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID productId;

    @Column (name = "prId", length = 225, nullable = false, unique = true)
    private Long prId;

    @Column (name = "name", length = 225, nullable = false)
    @NotBlank (message = "Name cannot be blank")
    private String name;

    @Column(name = "tcin", nullable = false, length = 255)
    private String tcin;

    @Column(name = "dpci", nullable = false, length = 255)
    private String dpci;

    @Column(name = "upc", nullable = false, length = 255)
    private String upc;

    @Column(name = "model", nullable = false, length = 255)
    private String model;

    @FutureOrPresent (message="created date time is not in the required format")
    @Column(name = "created_date_time", nullable = false, length = 255)
    private LocalDateTime createdDateTime;
}
