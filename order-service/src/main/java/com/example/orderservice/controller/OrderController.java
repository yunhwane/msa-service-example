package com.example.orderservice.controller;


import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final Environment env;


    @GetMapping("/health_check")
    public String status(){
        return String.format("It's Working in Order Service on PORT %s", env.getProperty("local.server.port"));
    }


    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable String userId, @RequestBody RequestOrder orderDetails){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = modelMapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);

        OrderDto createdOrderDto = orderService.createOrder(orderDto);
        ResponseOrder responseOrder = new ModelMapper().map(createdOrderDto, ResponseOrder.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable String userId){

        Iterable<Order> orderList = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> orderResponseList = new ModelMapper().map(orderList, new TypeToken<List<ResponseOrder>>(){}.getType());
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseList);
    }



}
