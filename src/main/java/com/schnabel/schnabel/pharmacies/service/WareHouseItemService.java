package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.pharmacies.repository.IWareHouseItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * WareHouseItem service implementation
 */
@Service
public class WareHouseItemService extends CrudService<WareHouseItem, Long> implements IWareHouseItemService
{
    @Autowired
    public WareHouseItemService(IWareHouseItemRepository wareHouseItemRepository)
    {
		  super(wareHouseItemRepository);
    }
}
