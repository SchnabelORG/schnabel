package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.pharmacies.repository.IWareHouseItemRepository;
import com.schnabel.schnabel.users.dto.PharmacistDTOAssembler;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.repository.IPharmacistRepository;
import com.schnabel.schnabel.users.service.IPharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * WareHouseItem service implementation
 */
@Service
public class WareHouseItemService extends JpaService<WareHouseItem, Long, IWareHouseItemRepository> implements IWareHouseItemService {


    @Autowired
    public WareHouseItemService(IWareHouseItemRepository repository){
        super(repository);
    }

    @Override
    public Optional<WareHouseItem> findWareHouseItemByPharmacyAndDrugId(Long drugId, Long pharmacyId) {
        return repository.findWareHouseItemByPharmacyAndDrugId(drugId, pharmacyId);
    }
}
