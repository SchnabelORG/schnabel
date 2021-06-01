package com.schnabel.schnabel.procurement.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.procurement.dto.OrderDTO;
import com.schnabel.schnabel.procurement.model.Order;
import com.schnabel.schnabel.procurement.model.OrderItem;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Order Service interface
 */
public interface IOrderService extends IJpaService<Order, Long>
{
    Optional<OrderDTO> getDTO(Long id);
    PagedModel<OrderDTO> getAllDTO(Pageable pageable);
    PagedModel<OrderDTO> getNonExpired(Pageable pageable);
    PagedModel<OrderDTO> getNewOrders(Pageable pageable, Long id);
    PagedModel<OrderDTO> getCreatedOrdersByPharmacyId(Long pharmacyId, Pageable pageable);
    boolean createNewOrder(String description, LocalDate deadline, List<OrderItem> orderItems, String email);
}
