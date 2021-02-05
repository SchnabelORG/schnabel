package com.schnabel.schnabel.order.controller;

import com.google.common.collect.Iterables;
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
    public ResponseEntity<Iterable<OrderItem>> add(@RequestBody Iterable<OrderItem> orderItems)
    {
        Iterable<OrderItem> oi = orderItemService.addOrderItems(orderItems);
        return Iterables.size(oi) != 0 ?
            ResponseEntity.ok(oi) :
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
