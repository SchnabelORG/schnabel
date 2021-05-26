package com.schnabel.schnabel.appointment.service;


import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

/**
 * Appointment Service interface
 */
public interface IAppointmentService extends IJpaService<Appointment, Long> {
    Optional<AppointmentDTO> getDTO(Long id);
    PagedModel<AppointmentDTO> getAllDTO(Pageable pageable);
    PagedModel<AppointmentDTO> getAllbyPharmacist(Pageable pageable, Long pharmacystId);
}
