package com.schnabel.schnabel.grades.service;

import com.schnabel.schnabel.grades.dto.ComplaintDTO;
import com.schnabel.schnabel.grades.dto.ComplaintDTOAssembler;
import com.schnabel.schnabel.grades.model.Complaint;
import com.schnabel.schnabel.grades.repository.IComplaintRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ComplaintService extends JpaService<Complaint, Long, IComplaintRepository> implements IComplaintService{

    private final ComplaintDTOAssembler complaintDTOAssembler;
    private final PagedResourcesAssembler<Complaint> complaintPagedResourcesAssemgler;

    @Autowired
    public ComplaintService(IComplaintRepository repository, ComplaintDTOAssembler complaintDTOAssembler, PagedResourcesAssembler<Complaint> complaintPagedResourcesAssemgler) {
        super(repository);
        this.complaintDTOAssembler = complaintDTOAssembler;
        this.complaintPagedResourcesAssemgler = complaintPagedResourcesAssemgler;
    }

    @Override
    @Transactional
    public Optional<ComplaintDTO> getDTO(Long id) {
        return get(id).map(complaintDTOAssembler::toModel);
    }

    @Override
    @Transactional
    public PagedModel<ComplaintDTO> getAllDTO(Pageable pageable) {
        Page<Complaint> complaints = getAll(pageable);
        return complaintPagedResourcesAssemgler.toModel(complaints, complaintDTOAssembler);
    }

    @Override
    @Transactional
    public PagedModel<ComplaintDTO> getWithoutResponse(Pageable pageable) {
        Page<Complaint> complaints = repository.findByResponse(pageable,null);
        return complaintPagedResourcesAssemgler.toModel(complaints, complaintDTOAssembler);
    }

    @Override
    public boolean addResponse(Long id, String response) {
        try {
            Complaint complaint = get(id).get();
            if(complaint != null)
            {
                complaint.setResponse(response);
                return update(complaint);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


}
