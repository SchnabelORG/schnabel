package com.schnabel.schnabel.appointment.service;

import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.appointment.dto.AppointmentDTOAssembler;
import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.appointment.repository.IAppointmentRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Appointment service implementation
 */
@Service
public class AppointmentService  extends JpaService<Appointment, Long, IAppointmentRepository> implements IAppointmentService {

    private final AppointmentDTOAssembler appointmentDTOAssembler;
    private final PagedResourcesAssembler<Appointment> appointmentPagedResourcesAssembler;


    @Autowired
    public AppointmentService(IAppointmentRepository repository, AppointmentDTOAssembler appointmentDTOAssembler, PagedResourcesAssembler<Appointment> appointmentPagedResourcesAssembler)
    {

        super(repository);
        this.appointmentDTOAssembler = appointmentDTOAssembler;
        this.appointmentPagedResourcesAssembler = appointmentPagedResourcesAssembler;
    }

    @Override
    @Transactional
    public Optional<AppointmentDTO> getDTO(Long id) {
        return get(id).map(appointmentDTOAssembler::toModel);
    }

    @Override
    @Transactional
    public PagedModel<AppointmentDTO> getAllDTO(Pageable pageable) {

        Page<Appointment> appointments = getAll(pageable);
        return appointmentPagedResourcesAssembler.toModel(appointments, appointmentDTOAssembler);
    }

    @Override
    @Transactional
    public PagedModel<AppointmentDTO> getAllbyPharmacist(Pageable pageable, Long pharmacystId) {
        Page<Appointment> appointments = repository.findByMedicalEmployeeId(pageable, pharmacystId);
        return appointmentPagedResourcesAssembler.toModel(appointments, appointmentDTOAssembler);
    }
}
