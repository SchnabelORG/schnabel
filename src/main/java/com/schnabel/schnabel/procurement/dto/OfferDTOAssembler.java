package com.schnabel.schnabel.procurement.dto;

import com.schnabel.schnabel.procurement.controller.OfferController;
import com.schnabel.schnabel.procurement.model.Offer;
import com.schnabel.schnabel.procurement.model.Order;
import com.schnabel.schnabel.procurement.model.OrderItem;
import com.schnabel.schnabel.users.controller.SupplierController;
import com.schnabel.schnabel.users.dto.SupplierDTO;
import com.schnabel.schnabel.users.model.Supplier;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OfferDTOAssembler extends RepresentationModelAssemblerSupport<Offer, OfferDTO> {

    public OfferDTOAssembler()
    {
        super(OfferController.class, OfferDTO.class);
    }

    @Override
    public OfferDTO toModel(Offer entity) {
        OfferDTO dto = instantiateModel(entity);

        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setDateOfDelivery(entity.getDateOfDelivery());
        Supplier supplier = entity.getSupplier();
        dto.setSupplier(SupplierDTO.builder()
            .id(supplier.getId())
            .firm(supplier.getFirm())
            .build()
            .add(linkTo(methodOn(SupplierController.class).get(supplier.getId())).withSelfRel()));
        
        //dto.setOrder(getOrder(entity.getOrder()));
        //dto.add(linkTo(methodOn(OrderController.class).get(entity.getOrder().getId())).withRel("order"));

        return dto;
    }

    private OrderForOfferDTO getOrder(Order order)
    {
        OrderForOfferDTO dto = new OrderForOfferDTO();
        dto.setId(order.getId());
        dto.setDeadline(order.getDeadline());
        dto.setDescription(order.getDescription());
        dto.setOrderItems(getOrderItems(order.getOrderItems()));

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