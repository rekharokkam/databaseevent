package com.learning.databaseevent.repository.entity;

import com.learning.databaseevent.customannotations.UniqueIdentifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "myorder")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column (name = "orderid", length = 225)
    private String orderId;

    @Column (name = "ordernumber", length = 225, nullable = true)
    private String orderNumber;

    @Column(name = "orderstatus", nullable = false, length = 255)
    private String orderStatus;

    @Column(name = "ordertype", nullable = false, length = 255)
    private String orderType;
}
