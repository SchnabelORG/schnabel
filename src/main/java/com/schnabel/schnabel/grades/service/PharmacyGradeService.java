package com.schnabel.schnabel.grades.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.service.IPatientService;

import java.util.Optional;

import com.schnabel.schnabel.grades.dto.RatingRequest;
import com.schnabel.schnabel.grades.model.PharmacyGrade;
import com.schnabel.schnabel.grades.repository.IPharmacyGradeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PharmacyGrade service implementation
 */
@Service
public class PharmacyGradeService extends JpaService<PharmacyGrade, Long, IPharmacyGradeRepository> implements IPharmacyGradeService
{
	private final JwtUtils jwtUtils;
	private final IPatientService patientService;
	private final IPharmacyService pharmacyService;

	@Autowired
    public PharmacyGradeService(IPharmacyGradeRepository repository, JwtUtils jwtUtils, IPatientService patientService, IPharmacyService pharmacyService) {
		super(repository);
		this.jwtUtils = jwtUtils;
		this.patientService = patientService;
		this.pharmacyService = pharmacyService;
	  }

    @Override
    public boolean ratePharmacy(RatingRequest req, String authHeader) {
		String email = jwtUtils.getEmailFromAuth(authHeader);
		Optional<Patient> patient = patientService.findByEmail(email);
		Optional<Pharmacy> pharmacy = pharmacyService.get(req.getTargetId());
		if(!patient.isPresent() || !pharmacy.isPresent()) {
			return false;
		}
		Optional<PharmacyGrade> grade = repository.findByPatientIdAndPharmacyId(patient.get().getId(), pharmacy.get().getId());
		if(grade.isPresent()) {
			grade.get().setValue(req.getValue());
			return update(grade.get());
		}

		PharmacyGrade newGrade = new PharmacyGrade(req.getValue(), pharmacy.get(), patient.get());
		return add(newGrade).isPresent();
    }

    
}
