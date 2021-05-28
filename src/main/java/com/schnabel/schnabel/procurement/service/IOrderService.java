package com.schnabel.schnabel.procurement.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.procurement.dto.OrderDTO;
import com.schnabel.schnabel.procurement.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

/**
 * Order Service interface
 */
public interface IOrderService extends IJpaService<Order, Long>
{
    Optional<OrderDTO> getDTO(Long id);
    PagedModel<OrderDTO> getNonExpired(Pageable pageable);
    PagedModel<OrderDTO> getNewOrders(Pageable pageable, Long id);
}
