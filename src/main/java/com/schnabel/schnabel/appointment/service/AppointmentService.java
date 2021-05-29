package com.schnabel.schnabel.appointment.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.appointment.dto.AppointmentDTOAssembler;
import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.appointment.repository.IAppointmentRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.model.Shift;
import com.schnabel.schnabel.users.service.IDermatologistService;
import com.schnabel.schnabel.users.service.IPharmacyAdminService;
import com.schnabel.schnabel.users.service.IShiftService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * Appointment service implementation
 */
@Service
public class AppointmentService extends JpaService<Appointment, Long, IAppointmentRepository> implements IAppointmentService
{

    private final IShiftService shiftService;
    private final AppointmentDTOAssembler appointmentDTOAssembler;
    private final PagedResourcesAssembler<Appointment> appointmentPagedResourcesAssembler;
    private final IDermatologistService dermatologistService;
    private final IPharmacyAdminService pharmacyAdminService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AppointmentService(IAppointmentRepository repository, IShiftService shiftService, AppointmentDTOAssembler appointmentDTOAssembler, PagedResourcesAssembler<Appointment> appointmentPagedResourcesAssembler, IDermatologistService dermatologistService, IPharmacyAdminService pharmacyAdminService, JwtUtils jwtUtils)
    {
		  super(repository);
          this.shiftService = shiftService;
          this.appointmentDTOAssembler = appointmentDTOAssembler;
          this.appointmentPagedResourcesAssembler = appointmentPagedResourcesAssembler;
          this.dermatologistService = dermatologistService;
          this.pharmacyAdminService = pharmacyAdminService;
          this.jwtUtils = jwtUtils;
	}

    /**
    * Pharmacy admin define appointment
    */
    @Override
    public boolean defineAppointment(LocalDateTime startTime, LocalDateTime endTime, double price, Long dermatologistId, String authHeader)
    {
        if(startTime.getDayOfMonth() != endTime.getDayOfMonth())
        {
            return false;
        }
       // String jws;
      //  if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
       //     jws = authHeader.substring(7, authHeader.length());
       //     String email = jwtUtils.getEmailFromJws(jws);

            System.out.println("header" + authHeader);
            if(checkAvailability(startTime, endTime, dermatologistId))
            {
                Appointment newAppointment = new Appointment(price, new Period(startTime, endTime), true, dermatologistService.get(dermatologistId).get(), pharmacyAdminService.findByEmail("jankovicpharmacy@gmail.com").get().getPharmacy());
                Optional<Appointment> appointment = add(newAppointment);
                if(appointment.isPresent())
                {
                    return true;
                }
            }
       // }
        return false;
    }

    /**
    * Checking availability(Pharmacy admin define appointment)
    */
    private boolean checkAvailability(LocalDateTime startTime, LocalDateTime endTime, Long dermatologistId)
    {
        Optional<Shift> shift = shiftService.getAllByMedicalEmployee(dermatologistId);
        List<Appointment> appointments = getAllByDermatologistForDay(dermatologistId, startTime);

        if (startTime.toLocalTime().isAfter(shift.get().getStartTime()) && endTime.toLocalTime().isBefore(shift.get().getEndTime()))
        {
            if(!appointments.isEmpty())
            {
                for (Appointment appointment : appointments) 
                {
                    if(appointment.getPeriod().getStartTime().isBefore(endTime) && startTime.isBefore(appointment.getPeriod().getEndTime()))
                    {
                        return false;
                    }
                }
            }
            return true;
        } 
        else 
        {
            return false;
        }
    }

    @Override
    @Transactional
    public List<Appointment> getAllByDermatologistForDay(Long dermatologistId, LocalDateTime date) 
    {
        List<Appointment> appointments = repository.findByMedicalEmployeeId(dermatologistId);
        List<Appointment> appointmentsForDay = new ArrayList<Appointment>();
        
        if(!appointments.isEmpty())
        {
            for (Appointment appointment : appointments) 
            {
                if(appointment.getPeriod().getStartTime().getDayOfMonth() == date.getDayOfMonth())
                {
                    appointmentsForDay.add(appointment);
                }    
            }
        }
        return appointmentsForDay;
    }
}