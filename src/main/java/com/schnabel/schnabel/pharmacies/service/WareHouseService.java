package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.pharmacies.model.WareHouse;
import com.schnabel.schnabel.pharmacies.repository.IWareHouseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * WareHouse service implementation
 */
@Service
public class WareHouseService extends CrudService<WareHouse, Long> implements IWareHouseService
{
    @Autowired
    public WareHouseService(IWareHouseRepository wareHouseRepository)
    {
		  super(wareHouseRepository);
    }
}
