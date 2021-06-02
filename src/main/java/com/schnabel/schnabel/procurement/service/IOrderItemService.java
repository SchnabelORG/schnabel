package com.schnabel.schnabel.procurement.service;

import java.util.List;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.procurement.model.OrderItem;


/**
 * OrderItem Service interface
 */
public interface IOrderItemService extends IJpaService<OrderItem, Long>
{
    List<OrderItem> findAllByOrderId(Long id);    
}
