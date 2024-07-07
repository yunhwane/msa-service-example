package com.example.catalogservice.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "catalog")
public class Catalog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;

    private LocalDateTime createdAt;
}
