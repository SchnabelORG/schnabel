package com.schnabel.schnabel.appointment.service;

import com.schnabel.schnabel.appointment.model.AppointmentReport;
import com.schnabel.schnabel.appointment.model.RecommendedMed;
import com.schnabel.schnabel.appointment.repository.IAppointmentReportRepository;
import com.schnabel.schnabel.appointment.repository.IRecommendedMedRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import org.springframework.stereotype.Service;

@Service
public class RecommendedMedService extends JpaService<RecommendedMed, Long, IRecommendedMedRepository> implements IRecommendedMedService{
    public RecommendedMedService(IRecommendedMedRepository repository) {
        super(repository);
    }
}
