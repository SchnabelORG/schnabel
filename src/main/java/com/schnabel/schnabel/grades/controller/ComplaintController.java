package com.schnabel.schnabel.grades.controller;

import com.schnabel.schnabel.grades.dto.ComplainRequset;
import com.schnabel.schnabel.grades.dto.ComplaintDTO;
import com.schnabel.schnabel.grades.dto.ComplaintRespsonseDTO;
import com.schnabel.schnabel.grades.service.IComplaintService;
import com.schnabel.schnabel.security.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/complaint")
public class ComplaintController {

    private final IComplaintService complaintService;
    private final JwtUtils jwtUtils;

    @Autowired
    public ComplaintController(IComplaintService complaintService, JwtUtils jwtUtils) {
        this.complaintService = complaintService;
        this.jwtUtils = jwtUtils;
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

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping("complain")
    public ResponseEntity<String> complain(@RequestBody ComplainRequset req,  @RequestHeader("Authorization") String auth) {
        String email = jwtUtils.getEmailFromJws(jwtUtils.parseJwtFromAuthorizationHeader(auth));
        return complaintService.createComplain(req.getText(), email) ?
                ResponseEntity.ok("Created")
                : ResponseEntity.badRequest().build();
    }


}
