package com.schnabel.schnabel.grades.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.service.IPatientService;

import java.util.Optional;

import com.schnabel.schnabel.grades.dto.GradeRequest;
import com.schnabel.schnabel.grades.model.PharmacyGrade;
import com.schnabel.schnabel.grades.repository.IPharmacyGradeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
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
    public boolean ratePharmacy(GradeRequest req, String authHeader) {
		if(req.getValue() < 0 || req.getValue() > 5) {
			return false;
		}
		String email = jwtUtils.getEmailFromAuth(authHeader);
		Optional<Patient> patient = patientService.findByEmail(email);
		Optional<Pharmacy> pharmacy = pharmacyService.get(req.getTargetId());
		if(!patient.isPresent() || !pharmacy.isPresent()) {
			return false;
		}
		if(!patientService.isAllowedToGradePharmacy(patient.get().getId(), pharmacy.get().getId())) {
			return false;
		}
		Optional<PharmacyGrade> grade = repository.findByPatientIdAndPharmacyId(patient.get().getId(), pharmacy.get().getId());
		if(grade.isPresent()) {
			grade.get().setValue(req.getValue());
			if(!update(grade.get())) {
				return false;
			}
			pharmacy.get().setScore(repository.findAvg(pharmacy.get().getId()));
			return pharmacyService.update(pharmacy.get());
		} else {
			PharmacyGrade newGrade = new PharmacyGrade(req.getValue(), pharmacy.get(), patient.get());
			add(newGrade);
			pharmacy.get().setScore(repository.findAvg(pharmacy.get().getId()));
			return pharmacyService.update(pharmacy.get());
		}
    }

	@Override
	public PagedModel<PharmacyDTO> findGraded(String authHeader, Pageable pageable) {
		String email = jwtUtils.getEmailFromAuth(authHeader);
		Optional<Patient> patient = patientService.findByEmail(email);
		if(!patient.isPresent()) {
			return PagedModel.empty();
		}
		return pharmacyService.findGraded(patient.get().getId(), pageable);
	}

	@Override
	public PagedModel<PharmacyDTO> findGradeable(String authHeader, Pageable pageable) {
		String email = jwtUtils.getEmailFromAuth(authHeader);
		Optional<Patient> patient = patientService.findByEmail(email);
		if(!patient.isPresent()) {
			return PagedModel.empty();
		}
		return pharmacyService.findGradeable(patient.get().getId(), pageable);
	}

	@Override
	public double findAvg(Long pharmacyId)
	{
		return repository.findAvg(pharmacyId);
	}
}
