package com.schnabel.schnabel.appointment.repository;


import com.schnabel.schnabel.appointment.model.AppointmentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "app_report", path = "appreport")
public interface IAppointmentReportRepository extends JpaRepository<AppointmentReport, Long> {
}
