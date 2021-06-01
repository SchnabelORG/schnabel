package com.schnabel.schnabel.drugs.service;

import java.util.Optional;

import com.schnabel.schnabel.drugs.dto.DrugPriceDTO;
import com.schnabel.schnabel.drugs.dto.DrugPriceDTOAssembler;
import com.schnabel.schnabel.drugs.model.DrugPrice;
import com.schnabel.schnabel.drugs.repository.IDrugPriceRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;

/**
 * DrugPrice JPA service implementation
 */
@Service
public class DrugPriceService extends JpaService<DrugPrice, Long, IDrugPriceRepository> implements IDrugPriceService {
    
    private final DrugPriceDTOAssembler dtoAssembler;
    private final PagedResourcesAssembler<DrugPrice> pageAsm;

    @Autowired
    public DrugPriceService(IDrugPriceRepository repository, DrugPriceDTOAssembler dtoAssembler, PagedResourcesAssembler<DrugPrice> pageAsm)
    {
        super(repository);
        this.dtoAssembler = dtoAssembler;
        this.pageAsm = pageAsm;
    }

    @Override
    public Optional<DrugPriceDTO> findByIdDTO(Long id) {
        return get(id).map(dtoAssembler::toModel);
    }
}