package com.schnabel.schnabel.grades.controller;

import com.schnabel.schnabel.grades.dto.ComplaintDTO;
import com.schnabel.schnabel.grades.dto.ComplaintRespsonseDTO;
import com.schnabel.schnabel.grades.service.IComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/complaint")
public class ComplaintController {

    private final IComplaintService complaintService;

    @Autowired
    public ComplaintController(IComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    /**
     * Get complaint by id
     * @param id
     * @return Complaint
     */
    @GetMapping("{id}")
    public ResponseEntity<ComplaintDTO> get(@PathVariable long id)
    {
        return complaintService.getDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    /**
     * Get all complaints
     * @return Page of Complaint
     */
    @GetMapping()
    public ResponseEntity<PagedModel<ComplaintDTO>> getAll(Pageable pageable)
    {
        return new ResponseEntity<>(complaintService.getAllDTO(pageable), HttpStatus.OK);
    }

    /**
     * Get complaints witout response
     * @return Page of Complaint
     */
    @GetMapping("toRespond")
    public ResponseEntity<PagedModel<ComplaintDTO>> getWithoutResponse(Pageable pageable)
    {
        return new ResponseEntity<>(complaintService.getWithoutResponse(pageable), HttpStatus.OK);
    }

    /**
     * Add response to complaint
     * @param respsonseDTO - complaintId, response
     * @return OK if responded, else BadRequest
     */
    @PostMapping("addRespond")
    public ResponseEntity<String> respond(@RequestBody ComplaintRespsonseDTO respsonseDTO)
    {
        return complaintService.addResponse(respsonseDTO.getId(), respsonseDTO.getResponse()) ?
                ResponseEntity.ok("Responded")
                : ResponseEntity.badRequest().build();
    }


}
