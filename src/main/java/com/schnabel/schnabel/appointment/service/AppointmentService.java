package com.schnabel.schnabel.appointment.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.appointment.dto.AppointmentDTO;
import com.schnabel.schnabel.appointment.dto.AppointmentDTOAssembler;
import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.appointment.repository.IAppointmentRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.users.dto.ShiftDTO;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.model.Shift;
import com.schnabel.schnabel.users.service.IShiftService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;


/**
 * Appointment service implementation
 */
@Service
public class AppointmentService extends JpaService<Appointment, Long, IAppointmentRepository> implements IAppointmentService
{

    private final IShiftService shiftService;
    private final AppointmentDTOAssembler appointmentDTOAssembler;
    private final PagedResourcesAssembler<Appointment> appointmentPagedResourcesAssembler;

    @Autowired
    public AppointmentService(IAppointmentRepository repository, IShiftService shiftService, AppointmentDTOAssembler appointmentDTOAssembler, PagedResourcesAssembler<Appointment> appointmentPagedResourcesAssembler)
    {
		  super(repository);
          this.shiftService = shiftService;
          this.appointmentDTOAssembler = appointmentDTOAssembler;
          this.appointmentPagedResourcesAssembler = appointmentPagedResourcesAssembler;
	}

    @Override
    public boolean defineAppointment(LocalDateTime startTime, LocalDateTime endTime, double price, MedicalEmployee medicalEmployee, Pageable pageable)
    {
        // todo
        //get shift
        //check availability and is the term free or not

        Optional<Shift> shift = shiftService.getAllByMedicalEmployee(pageable, medicalEmployee.getId());
        PagedModel<AppointmentDTO> appointmentDTO = getAllByDermatologist(pageable, medicalEmployee.getId());

        if (startTime.toLocalTime().isAfter(shift.get().getStartTime()) && endTime.toLocalTime().isBefore(shift.get().getEndTime()))
        {

        } else {
            return false;
        }

        Appointment newAppointment = new Appointment(price, new Period(startTime, endTime), true, medicalEmployee);
        Optional<Appointment> appointment = add(newAppointment);
        if(appointment.isPresent())
        {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public PagedModel<AppointmentDTO> getAllByDermatologist(Pageable pageable, Long dermatologistId) {
        Page<Appointment> appointments = repository.findByMedicalEmployeeId(pageable, dermatologistId);
        return appointmentPagedResourcesAssembler.toModel(appointments, appointmentDTOAssembler);
    }
}