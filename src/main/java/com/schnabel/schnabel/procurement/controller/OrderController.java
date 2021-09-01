package com.schnabel.schnabel.procurement.controller;

import com.schnabel.schnabel.procurement.dto.OrderDTO;
import com.schnabel.schnabel.procurement.dto.OrderRequest;
import com.schnabel.schnabel.procurement.dto.OrderUpdateRequest;
import java.util.Optional;

import com.schnabel.schnabel.procurement.dto.OrderDTO;
import com.schnabel.schnabel.procurement.model.Order;
import com.schnabel.schnabel.procurement.service.IOrderService;
import com.schnabel.schnabel.security.util.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    private final JwtUtils jwtUtils;

    @Autowired
    public OrderController(IOrderService orderService, JwtUtils jwtUtils)
    {
        this.orderService = orderService;
        this.jwtUtils = jwtUtils;
    }


    /**
     * Get all orders
     * @return Iterable of Order
    */
    @GetMapping()
    public ResponseEntity<PagedModel<OrderDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(orderService.getAllDTO(pageable), HttpStatus.OK);
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

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @GetMapping("active")
    public ResponseEntity<PagedModel<OrderDTO>> getActive(Pageable pageable)
    {
        return new ResponseEntity<>(orderService.getNonExpired(pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @GetMapping("new")
    public ResponseEntity<PagedModel<OrderDTO>> getNewOrders(Pageable pageable, @RequestHeader("Authorization") String authHeader)
    {
        String jws;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jws = authHeader.substring(7, authHeader.length());
        } else {
            return ResponseEntity.badRequest().build();
        }

        String email = jwtUtils.getEmailFromJws(jws);

        return new ResponseEntity<>(orderService.getNewOrders(pageable, email), HttpStatus.OK);
    }

    @GetMapping("pharmacy/{id}")
    public ResponseEntity<PagedModel<OrderDTO>> getCreatedOrdersByPharmacyId(@PathVariable("id") Long pharmacyId, Pageable pageable)
    {
        return new ResponseEntity<>(orderService.getCreatedOrdersByPharmacyId(pharmacyId, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createNewOrder(@RequestBody OrderRequest req, @RequestHeader("Authorization") String authHeader)
    {
        String jws = jwtUtils.parseJwtFromAuthorizationHeader(authHeader);
        return orderService.createNewOrder(req.getDescription(), req.getDeadline(), req.getOrderItems(), jwtUtils.getEmailFromJws(jws)) ?
            ResponseEntity.ok("Added")
            : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long orderId, @RequestHeader("Authorization") String authHeader, Pageable pageable)
    {
        return orderService.deleteOrder(pageable, orderId) ? 
            ResponseEntity.ok("Deleted")
            : ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<String> updateOrder(@RequestBody OrderUpdateRequest orderUpdateRequest, @RequestHeader("Authorization") String authHeader, Pageable pageable)
    {
        return orderService.updateOrder(orderUpdateRequest, pageable) ?
            ResponseEntity.ok("Updated")
            : ResponseEntity.badRequest().build();
    }
}