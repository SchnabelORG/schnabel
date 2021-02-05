package com.schnabel.schnabel.terms.service;

import java.util.ArrayList;
import java.util.List;

import com.schnabel.schnabel.misc.implementations.CrudService;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.terms.model.Term;
import com.schnabel.schnabel.terms.repository.ITermRepository;
import com.schnabel.schnabel.users.model.EmployedUser;

import org.springframework.stereotype.Service;

/**
 * Term service implementation
 */
@Service
public class TermService extends CrudService<Term, Long> implements ITermService
{
    public TermService(ITermRepository repository)
    {
        super(repository);
    }

    public boolean createTerm(int duration, EmployedUser employedUser, double price, Pharmacy pharmacy)
    {
        /*List<Term> terms = new ArrayList<>();


        int startTime = doctorWorkDays.StartTime;
        int endTime = doctorWorkDays.EndTime;
        
        DateTime appointmentStart = new DateTime(date.Year, date.Month, date.Day, startTime, 0, 0);

        for (int i = 0; i < (endTime - startTime)*2; i++)
        {
            Appointment appointment = new Appointment
            {
                DoctorId = doctorId,
                Doctor = doctorWorkDays.Doctor,
                Period = new Period(appointmentStart.AddMinutes(appointmentDuration * i), appointmentStart.AddMinutes(appointmentDuration * (i + 1)))
            };
            appointments.Add(appointment);
        }*/
        return true;
    }


}
