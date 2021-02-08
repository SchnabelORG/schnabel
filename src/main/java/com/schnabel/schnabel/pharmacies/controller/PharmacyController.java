package com.schnabel.schnabel.pharmacies.controller;

import com.schnabel.schnabel.misc.exceptions.PharmacyAlreadyExistsException;
import com.schnabel.schnabel.pharmacies.DTO.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.DTO.PharmacyToAdminDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;

import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.service.IPharmacyAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Pharmacy REST controller
 */
@RestController
public class PharmacyController
{
    private final IPharmacyService pharmacyService;
    private final IPharmacyAdminService pharmacyAdminService;

    @Autowired
    public PharmacyController(IPharmacyService pharmacyService, IPharmacyAdminService pharmacyAdminService)
    {
        this.pharmacyService = pharmacyService;
        this.pharmacyAdminService = pharmacyAdminService;
    }

    /**
     * Get pharmacy by <b>id</b>
     * @param id ID of queried pharmacy
     * @return 
     * OK with pharmacy if exists
     * else BAD_REQUEST
     */
    @GetMapping("/api/pharmacy/{id}")
    public ResponseEntity<Pharmacy> getById(@PathVariable long id)
    {
        Pharmacy pharmacy = pharmacyService.get(id);
        return pharmacy == null ?
            new ResponseEntity<>(HttpStatus.BAD_REQUEST)
            : ResponseEntity.ok(pharmacy);
    }

    /**
     * Get all pharmacies
     * @return Iterable of Pharmacy
     */
    @GetMapping("/api/pharmacy")
    public ResponseEntity<Iterable<Pharmacy>> getAll()
    {
        return ResponseEntity.ok(pharmacyService.getAll());
    }

    /**
     *
     * Register new pharmacy
     * @param pharmacyDTO
     * @return Status of successful pharmacy registration
     */
    @PostMapping("/api/pharmacy/register")
    public ResponseEntity<String> register( @RequestBody PharmacyDTO pharmacyDTO) {
        try {
            pharmacyService.registerNewPharmacy(pharmacyDTO);
        } catch (PharmacyAlreadyExistsException aex) {
            return new ResponseEntity<>(aex.toString(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Pharamcy registered!", HttpStatus.OK);
    }

    @PostMapping("/api/pharmacy/admin")
    public ResponseEntity<String> assign(@RequestBody PharmacyToAdminDTO  pharmacyToAdminDTO)
    {
        Pharmacy pharmacy = pharmacyService.findByName(pharmacyToAdminDTO.getPharmacyName());
        if(pharmacy == null)
            return new ResponseEntity<>("Pharmacy not found!", HttpStatus.BAD_REQUEST);
        PharmacyAdmin pharmacyAdmin = pharmacyAdminService.SetAdminToPharmacy(pharmacyToAdminDTO.getPharmacyAdminEmail(), pharmacy);
        if(pharmacyAdmin == null)
            return new ResponseEntity<>("Pharmacy admin not found!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("Admin assigned to pharmacy!", HttpStatus.OK);
    }
}
