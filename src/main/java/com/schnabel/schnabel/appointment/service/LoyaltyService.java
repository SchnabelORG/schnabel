package com.schnabel.schnabel.appointment.service;

import com.schnabel.schnabel.appointment.model.AppointmentReport;
import com.schnabel.schnabel.appointment.model.LoyaltyProgram;
import com.schnabel.schnabel.appointment.repository.IAppointmentReportRepository;
import com.schnabel.schnabel.appointment.repository.ILoyaltyRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyService extends JpaService<LoyaltyProgram, Long, ILoyaltyRepository> implements ILoyaltyService {

    public LoyaltyService(ILoyaltyRepository repository) {
        super(repository);
    }
}
