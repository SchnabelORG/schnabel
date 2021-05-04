package com.schnabel.schnabel.procurement.controller;

import com.schnabel.schnabel.procurement.model.Order;
import com.schnabel.schnabel.procurement.service.IOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * Get all orders
     * @return Iterable of Order
     */
    @GetMapping("/api/order")
    public ResponseEntity<Iterable<Order>> getAll()
    {
        Iterable<Order> orders = orderService.getAll();
        return ResponseEntity.ok(orders);
    }

    /**
     * Get order by id
     * @return Order
     */
    @GetMapping("/api/order/{id}")
    public ResponseEntity<Order> get(@PathVariable long id)
    {
        Order order = orderService.get(id);
        return order == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(order);
    }
}