package com.schnabel.schnabel.users.controller;

import java.util.Optional;

import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.ShiftDTO;
import com.schnabel.schnabel.users.dto.ShiftDTOAssembler;
import com.schnabel.schnabel.users.dto.ShiftRequest;
import com.schnabel.schnabel.users.model.Shift;
import com.schnabel.schnabel.users.service.IShiftService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Shift REST controller
 */
@RestController
@RequestMapping("api/shift")
public class ShiftController 
{
    private final IShiftService shiftService;
    private final ShiftDTOAssembler shiftDTOAssembler;
    private final PagedResourcesAssembler<Shift> shiftPageAsm;
    private final JwtUtils jwtUtils;

    @Autowired
    public ShiftController(IShiftService shiftService, ShiftDTOAssembler shiftDTOAssembler, PagedResourcesAssembler<Shift> shiftPageAsm, JwtUtils jwtUtils)
    {
        this.shiftService = shiftService;
        this.shiftDTOAssembler = shiftDTOAssembler;
        this.shiftPageAsm = shiftPageAsm;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Get all shifts
     * @return Iterable of Shift
     */
    @GetMapping
    public ResponseEntity<PagedModel<ShiftDTO>> getAll(Pageable pageable)
    {
        Page<Shift> shifts = shiftService.getAll(pageable);
        PagedModel<ShiftDTO> pagedModel = shiftPageAsm.toModel(shifts, shiftDTOAssembler);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    /**
     * Get shifts by id
     * @return Shift
     */
    @GetMapping("{id}")
    public ResponseEntity<ShiftDTO> get(@PathVariable Long id)
    {
        return shiftService.get(id).map(shiftDTOAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get shift by dermatologist id and pharmacy id
     * @return Shift
     */
    @GetMapping("medicalemployeepharmacy/{medical_employee_id}/{pharmacy_id}")
    public ResponseEntity<Optional<ShiftDTO>> getShiftMedicalEmployeePharmacy(@PathVariable("medical_employee_id") Long medicalEmployeeId, @PathVariable("pharmacy_id") Long pharmacyId)
    {
        return new ResponseEntity<>(shiftService.getShiftMedicalEmployeePharmacy(medicalEmployeeId, pharmacyId), HttpStatus.OK);
    }

    @PostMapping("dermatologist")
    public ResponseEntity<String> defineDermatologistShift(@RequestBody ShiftRequest shiftRequest, @RequestHeader("Authorization") String authHeader)
    {
        String email = jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(authHeader));
        return shiftService.defineDermatologistShift(shiftRequest, email) ?
            ResponseEntity.ok("Added")
            : ResponseEntity.badRequest().build();
    }

    @PostMapping("pharmacist")
    public ResponseEntity<String> definePharmacistShift(@RequestBody ShiftRequest shiftRequest, @RequestHeader("Authorization") String authHeader)
    {
        String email = jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(authHeader));
        return shiftService.definePharmacistShift(shiftRequest, email) ?
            ResponseEntity.ok("Added")
            : ResponseEntity.badRequest().build();
    }
}
