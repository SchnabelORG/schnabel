package com.schnabel.schnabel.order.controller;

import com.schnabel.schnabel.order.model.OrderItem;
import com.schnabel.schnabel.order.service.IOrderItemService;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * OrderItem REST controller
 */
@RestController
public class OrderItemController 
{
    private final IOrderItemService orderItemService;

    @Autowired
    public OrderItemController(IOrderItemService orderItemService)
    {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/api/orderitem")
    public ResponseEntity<String> add(@RequestBody Iterable<OrderItem> orderItems)
    {
        return orderItemService.addOrderItems(orderItems) ?
            ResponseEntity.ok("Successfully added") :
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
