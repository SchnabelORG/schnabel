package com.schnabel.schnabel.pharmacies.controller;

import java.time.LocalDateTime;
import java.util.Map;

import com.schnabel.schnabel.pharmacies.dto.PharmacyCreationDTO;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.dto.UploadFileResponse;
import com.schnabel.schnabel.pharmacies.service.FileStorageService;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;

/**
 * Pharmacy REST controller
 */
@RestController
@RequestMapping("api/pharmacy")
public class PharmacyController
{
    private final IPharmacyService service;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    public PharmacyController(IPharmacyService pharmacyService)
    {
        this.service = pharmacyService;
    }

    /**
     * Get pharmacy based on <b>id</b>
     * @param id Pharmacy id
     * @return OK response with pharmacy with matching <b>id</b> if it exists
     * else NOT_FOUND
     */
    @Transactional
    @GetMapping("{id}")
    public ResponseEntity<PharmacyDTO> getById(@PathVariable Long id)
    {
        return service.getDTO(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("search")
    public ResponseEntity<PagedModel<PharmacyDTO>> filteredSearch(@RequestParam Map<String, String> params, Pageable pageable) {
        return new ResponseEntity<>(service.filteredSearch(params, pageable), HttpStatus.OK);
    }

    /**
     * Get all pharmacies
     * @return OK response containing all pharmacies
     */
    @GetMapping
    public ResponseEntity<PagedModel<PharmacyDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(service.getAllDTO(pageable), HttpStatus.OK);
    }

    /**
     * Register new Pharmacy
     * @param creationDTO - Pharmacy registration required info
     * @return OK if registered, else BadRequest
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("register")
    public ResponseEntity<String> registerPharamcy(@RequestBody PharmacyCreationDTO creationDTO)
    {
        return service.registerPharmacy(creationDTO) ?
                ResponseEntity.ok("Registered")
                : ResponseEntity.badRequest().build();
    }
    
    @GetMapping("check/{pharmacyName}")
    public ResponseEntity<?> checkByName(@PathVariable("pharmacyName") String pharmacyName) {
        return service.findByName(pharmacyName).isPresent() ?
            ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }

    /**
     * Returns pharmacies that have free pharmacist appointments at date(time)
     */
    @GetMapping("phappt/{date}")
    public ResponseEntity<PagedModel<PharmacyDTO>> getWithPhAppts(@PathVariable("date") LocalDateTime startTime, Pageable pageable) {
        return new ResponseEntity<>(service.findByFreePharmacistAppointment(startTime, pageable), HttpStatus.OK);
    }

    /**
     * Return pharmacies that have specific drug in stock
     */
    @GetMapping("drug/{id}")
    public ResponseEntity<PagedModel<PharmacyDTO>> getWithStock(@PathVariable("id") Long drugId, Pageable pageable) {
        return new ResponseEntity<>(service.findWithStock(drugId, pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

}

