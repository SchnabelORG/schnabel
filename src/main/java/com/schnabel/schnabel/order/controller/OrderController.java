package com.schnabel.schnabel.order.controller;

import com.schnabel.schnabel.order.service.IOrderService;
import com.schnabel.schnabel.order.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Order REST controller
 */
@RestController
public class OrderController 
{
    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService)
    {
        this.orderService = orderService;
    }

    @GetMapping("/api/order/{id}")
    public ResponseEntity<Order> getById(@PathVariable int id)
    {
        Order order = orderService.get(id);
        return order == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(order);
    }

    @GetMapping("/api/orders")
    public ResponseEntity<Iterable<Order>> getAll()
    {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/api/expiredorders")
    public ResponseEntity<Iterable<Order>> getAllExpired()
    {
        return ResponseEntity.ok(orderService.getExpiredOrders());
    }

    @PostMapping("/api/order")
    public ResponseEntity<Order> add(@RequestBody Order order)
    {
        return orderService.add(order) != null ?
            ResponseEntity.ok(order) :
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
