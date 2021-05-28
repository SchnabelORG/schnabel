package com.schnabel.schnabel.procurement.controller;

import java.util.Optional;

import com.schnabel.schnabel.procurement.dto.OrderDTO;
import com.schnabel.schnabel.procurement.model.Order;
import com.schnabel.schnabel.procurement.service.IOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Order REST controller
 */
@RestController
@RequestMapping("api/order")
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
    @GetMapping()
    public ResponseEntity<Iterable<Order>> getAll()
    {
        Iterable<Order> orders = orderService.getAll();
        return ResponseEntity.ok(orders);
    }

    /**
     * Get order by id
     * @return Order
     */
    @GetMapping("{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable long id)
    {
        return orderService.getDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("active")
    public ResponseEntity<PagedModel<OrderDTO>> getActive(Pageable pageable)
    {
        return new ResponseEntity<>(orderService.getNonExpired(pageable), HttpStatus.OK);
    }

    @GetMapping("new/{id}")
    public ResponseEntity<PagedModel<OrderDTO>> getNewOrders(Pageable pageable, @PathVariable long id)
    {
        return new ResponseEntity<>(orderService.getNewOrders(pageable, id), HttpStatus.OK);
    }
}