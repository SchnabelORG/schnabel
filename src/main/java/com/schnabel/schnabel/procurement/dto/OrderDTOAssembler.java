package com.schnabel.schnabel.procurement.dto;

import com.schnabel.schnabel.procurement.controller.OrderController;
import com.schnabel.schnabel.procurement.model.Order;
import com.schnabel.schnabel.procurement.model.OrderItem;
import com.schnabel.schnabel.users.controller.PharmacyAdminController;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTO;
import com.schnabel.schnabel.users.model.PharmacyAdmin;

import com.schnabel.schnabel.procurement.controller.OfferController;
import com.schnabel.schnabel.procurement.controller.OrderController;
import com.schnabel.schnabel.procurement.model.Order;
import com.schnabel.schnabel.procurement.model.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderDTOAssembler extends RepresentationModelAssemblerSupport<Order, OrderDTO> {

    public OrderDTOAssembler()
    {
        super(OrderController.class, OrderDTO.class);
    }

    @Override
    public OrderDTO toModel(Order entity) {
        OrderDTO dto = instantiateModel(entity);

        dto.setId(entity.getId());
        dto.setDeadline(entity.getDeadline());
        dto.setDescription(entity.getDescription());
        PharmacyAdmin pharmacyAdmin = entity.getPharmacyAdmin();
        dto.setPharmacyAdmin(PharmacyAdminDTO.builder()
            .id(pharmacyAdmin.getId())
            .build()
            .add(linkTo(methodOn(PharmacyAdminController.class).get(pharmacyAdmin.getId())).withSelfRel()));

        //dto.add(linkTo(methodOn(OfferController.class).getByOrder(Pageable.unpaged(), entity.getId())).withRel("offers"));
        dto.setOrderItems(getOrderItems(entity.getOrderItems()));
        //dto.add(linkTo(methodOn(PharmacyAdminController.class).get(entity.getPharmacyAdmin().getId())).withRel("pharmacyAdmin"));
        dto.add(linkTo(methodOn(OfferController.class).getByOrder(Pageable.unpaged(), entity.getId())).withRel("offers"));
        return dto;
    }

    private List<OrderItemDTO> getOrderItems(List<OrderItem> items)
    {
        if(items.isEmpty())
            return Collections.emptyList();

        return items.stream()
                .map(p -> OrderItemDTO.builder()
                        .id(p.getId())
                        .quantity(p.getQuantity())
                        .drug(p.getDrug().getName())
                        .build())
                .collect(Collectors.toList());
    }
}
