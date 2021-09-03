package com.schnabel.schnabel.pharmacies.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.*;

import javax.transaction.Transactional;

import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.repository.IDrugRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.dto.*;
import com.schnabel.schnabel.pharmacies.dto.PharmacyCreationDTO;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTOAssembler;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDrugDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.pharmacies.repository.IPharmacyRepository;
import com.schnabel.schnabel.pharmacies.repository.IWareHouseItemRepository;
import com.schnabel.schnabel.pharmacies.repository.PharmacySpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

/**
 * Implementation of pharmacy service
 */
@Service
public class PharmacyService extends JpaService<Pharmacy, Long, IPharmacyRepository> implements IPharmacyService
{

    private final PharmacyDTOAssembler dtoAsm;
    private final PagedResourcesAssembler<Pharmacy> pageAsm;
    private static final long CONSULT_DURATION_MINUTES = 15;
    private final IDrugRepository drugRepository;
    private final IWareHouseItemRepository wareHouseRepository;

    @Autowired
    public PharmacyService(IPharmacyRepository pharmacyRepository, PharmacyDTOAssembler pharmacyDTOasm, PagedResourcesAssembler<Pharmacy> pharmacyPageAsm, IDrugRepository drugRepository, IWareHouseItemRepository wareHouseRepository)
    {
		  super(pharmacyRepository);
          this.dtoAsm = pharmacyDTOasm;
          this.pageAsm = pharmacyPageAsm;
        this.drugRepository = drugRepository;
        this.wareHouseRepository = wareHouseRepository;
    }

    @Override
    public boolean registerPharmacy(PharmacyCreationDTO creationDTO) {
        Pharmacy newPharmacy = new Pharmacy(creationDTO.getName(), creationDTO.getAddress());
        Optional<Pharmacy> pharmacy = add(newPharmacy);
        return pharmacy.isPresent();
    }

    @Override
    @Transactional
    public Optional<PharmacyDTO> getDTO(Long id) {
        return get(id)
            .map(dtoAsm::toModel);
    }

    @Override
    @Transactional
    public PagedModel<PharmacyDTO> getAllDTO(Pageable pageable) {
        Page<Pharmacy> pharmacies = getAll(pageable);
        return pageAsm.toModel(pharmacies, dtoAsm);
    }

    @Override
    @Transactional
    public PagedModel<PharmacyDTO> filteredSearch(Map<String, String> params, Pageable pageable) {
        Page<Pharmacy> pharmacies = repository.findAll(PharmacySpecification.filteredQuery(params), pageable);
        return pageAsm.toModel(pharmacies, dtoAsm);
    }

    @Override
    public Optional<Pharmacy> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public PagedModel<PharmacyDTO> findByFreePharmacistAppointment(LocalDateTime startTime, Pageable pageable) {
        Page<Pharmacy> pharmacies = repository.findByFreePharmacistAppointment(startTime, startTime.plusMinutes(CONSULT_DURATION_MINUTES), pageable);
        return pageAsm.toModel(pharmacies, dtoAsm);
    }
    /*
    @Override
    public PagedModel<PharmacyDTO> findWithStock(Long drugId, Pageable pageable) {
        Page<Pharmacy> pharmacies = repository.findWithStock(drugId, pageable);
        return pageAsm.toModel(pharmacies, dtoAsm);
    }*/

    @Transactional
    @Override
    public Collection<PharmacyDrugDTO> findWithStock(Long drugId, Pageable pageable) {
        Page<Pharmacy> pharmacies = repository.findWithStock(drugId, pageable);
        List<PharmacyDrugDTO> ph = new ArrayList<>();
        for(Pharmacy p: pharmacies){
            PharmacyDrugDTO pddto = new PharmacyDrugDTO(p.getId(), p.getName(), p.getAddress().getCity(), p.getAddress().getStreet());
            Optional<WareHouseItem> wareHouseItem = wareHouseRepository.findByPharmacyIdAndDrugId(drugId, p.getId());
            double price;
            if(!wareHouseItem.isPresent()) {
                price = 0;
            }
            price = wareHouseItem.get().getPriceForToday().getPrice();
            pddto.setPrice(price);
            ph.add(pddto);
        }
        return ph;
        /*pharmacies.stream()
                .map(p -> new PharmacyDrugDTO(p.getId(), p.getName(), p.getAddress().getCity(), p.getAddress().getStreet(), wareHouseItemService.getPrice(p.getId(), drugId)))
                .collect(Collectors.toList());
        return pageAsm.toModel(pharmacies, dtoAsm);*/
    }

    @Override
    public PagedModel<PharmacyDTO> findGraded(Long patientId, Pageable pageable) {
        Page<Pharmacy> pharmacies = repository.findGraded(patientId, pageable);
        return pageAsm.toModel(pharmacies, dtoAsm);
    }

    @Override
    public PagedModel<PharmacyDTO> findGradeable(Long patientId, Pageable pageable) {
        Page<Pharmacy> pharmacies = repository.findGradeable(patientId, pageable);
        return pageAsm.toModel(pharmacies, dtoAsm);
    }

    @Transactional
    @Override
    public List<PharmacyDrugDTO> findForERecipe(ERecipeDTO dto) {
        List<Pharmacy> pharmaciesList = repository.findAll();
        List<Drug> drugs = new ArrayList<>();
        for(ERecipeDrugDTO d: dto.getDrugs()) {
            Optional<Drug> drug = drugRepository.findByName(d.getName());
            drugs.add(drug.get());
            List<Pharmacy> pharmacies = repository.findForER(drug.get().getId(), d.getQuantity());
            List<Pharmacy> newList = new ArrayList<>();
            for(Pharmacy p : pharmacies){
                if(pharmaciesList.contains(p)){
                    newList.add(p);
                }
            }
            pharmaciesList = newList;
        }

        List<PharmacyDrugDTO> ph = new ArrayList<>();
        for(Pharmacy p: pharmaciesList){
            PharmacyDrugDTO pddto = new PharmacyDrugDTO(p.getId(), p.getName(), p.getAddress().getCity(), p.getAddress().getStreet());
            double price = 0;
            for(Drug d: drugs) {
                Optional<WareHouseItem> wareHouseItem = wareHouseRepository.findByPharmacyIdAndDrugId(d.getId(), p.getId());
                if (!wareHouseItem.isPresent()) {
                    price += 0;
                }
                price += wareHouseItem.get().getPriceForToday().getPrice();
                pddto.setPrice(price);
                ph.add(pddto);
            }
        }
        return ph;

    }

}
