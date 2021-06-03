package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.users.dto.ConsultRequest;
import com.schnabel.schnabel.users.dto.DrugReservationRequest;
import com.schnabel.schnabel.users.model.Patient;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * Patient service interface
 */
public interface IPatientService extends IJpaService<Patient, Long>
{
    Optional<Patient> findByEmail(String email);
    boolean scheduleDermAppt(Long apptId, String email);
    boolean cancelAppointment(Long apptId, String email);
    boolean registerPatient(String name, String surname, String email, String password, Address address, String phoneNo);
    boolean activate(String token);
    PagedModel<AppointmentDTO> findAppointments(String email, Pageable pageable);
    PagedModel<AppointmentDTO> findDermAppts(String email, Pageable pageable);
    boolean scheduleConsult(ConsultRequest req, String email);
    boolean reserveDrug(DrugReservationRequest req, String email);
    boolean isAllowedToGradePharmacy(Long patientId, Long pharmacyId);
    boolean isAllowedToGradeEmployee(Long patientId, Long employeeId);
}