package com.example.orderservice.dto;

import lombok.Data;


import java.time.LocalDateTime;


@Data
public class OrderDto {
    private Long id;
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String userId;
    private String orderId;
    private LocalDateTime createdAt;
}
