package com.schnabel.schnabel.grades.service;

import com.schnabel.schnabel.grades.dto.ComplaintDTO;
import com.schnabel.schnabel.grades.model.Complaint;
import com.schnabel.schnabel.misc.interfaces.IJpaService;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

public interface IComplaintService extends IJpaService<Complaint, Long> {
    Optional<ComplaintDTO> getDTO(Long id);
    PagedModel<ComplaintDTO> getAllDTO(Pageable pageable);
    PagedModel<ComplaintDTO> getWithoutResponse(Pageable pageable);
    boolean addResponse(Long id, String response);
    boolean createComplain(String text, String email);
}
