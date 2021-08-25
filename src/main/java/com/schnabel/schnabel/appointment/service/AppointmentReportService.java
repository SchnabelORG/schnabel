package com.schnabel.schnabel.appointment.service;


import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.appointment.model.AppointmentReport;
import com.schnabel.schnabel.appointment.repository.IAppointmentReportRepository;
import com.schnabel.schnabel.appointment.repository.IAppointmentRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import org.springframework.stereotype.Service;

/**
 * AppointmentReport service implementation
 */
@Service
public class AppointmentReportService extends JpaService<AppointmentReport, Long, IAppointmentReportRepository> implements IAppointmentReportService{
    public AppointmentReportService(IAppointmentReportRepository repository) {
        super(repository);
    }
}
