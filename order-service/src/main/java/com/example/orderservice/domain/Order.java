package com.example.orderservice.domain;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String userId;

    @Column(nullable = false, unique = true)
    private String orderId;

    private LocalDateTime createdAt;

}
