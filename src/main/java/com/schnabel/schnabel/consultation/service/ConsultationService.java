package com.schnabel.schnabel.consultation.service;

import java.time.LocalDateTime;

import com.schnabel.schnabel.consultation.dto.ConsultationDTO;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.pharmacies.model.Term;
import com.schnabel.schnabel.pharmacies.service.ITermService;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.service.IPatientService;
import com.schnabel.schnabel.users.service.IPharmacistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Consultation service implementation
 */
@Service
public class ConsultationService implements IConsultationService
{
    private final IPharmacistService pharmacistService;
    private final IPatientService patientService;
    private final ITermService termService;

    @Autowired
    public ConsultationService(IPharmacistService pharmacistService, IPatientService patientService, ITermService termService)
    {
        this.pharmacistService = pharmacistService;
        this.patientService = patientService;
        this.termService = termService;
    }

	@Override
    public boolean scheduleConsultation(ConsultationDTO dto)
    {
        if(dto.getStartSchedule().compareTo(LocalDateTime.now()) < 0)
        {
            return false;
        }

        Pharmacist pharmacist = this.pharmacistService.get(dto.getPharmacistId());
        Patient patient = this.patientService.get(dto.getPatientId());

        if(pharmacist == null || patient == null)
        {
            return false;
        }

        // TODO(Jovan): Check if schedule is free
        // TODO(Jovan): Move logic of getting free 'terms' to termService
        long consultDurationMinutes = 30;
        Term term = new Term(new Period(dto.getStartSchedule(), dto.getStartSchedule().plusMinutes(consultDurationMinutes)),
            pharmacist.getPharmacy(), pharmacist, patient);
        Term identifiedTerm;
        if((identifiedTerm = this.termService.add(term)) == null)
        {
            return false;
        }

        return this.pharmacistService.addTerm(pharmacist, identifiedTerm)
            && this.patientService.addTerm(patient, identifiedTerm);
	}

	@Override
    public boolean cancelConsultation(Long id)
    {
        Term term = this.termService.get(id);
        if(term == null)
        {
            return false;
        }
        Pharmacist pharmacist = this.pharmacistService.get(term.getEmployedUser().getId());
        Patient patient = this.patientService.get(term.getPatient().getId());
        return this.termService.remove(id)
            && this.pharmacistService.removeTerm(pharmacist, term)
            && this.patientService.removeTerm(patient, term);
	}
    
}
