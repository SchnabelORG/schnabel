package com.schnabel.schnabel.appointment.dto;
import com.schnabel.schnabel.appointment.controller.AppointmentController;
import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.users.controller.PatientController;
import com.schnabel.schnabel.users.dto.MedicalEmployeeDTO;
import com.schnabel.schnabel.users.dto.PatientDTO;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.model.Patient;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.time.format.DateTimeFormatter;

/**
 * Assembles AppointmentDTO
 */
@Component
public class AppointmentDTOAssembler extends RepresentationModelAssemblerSupport<Appointment, AppointmentDTO> {

    public AppointmentDTOAssembler() {
        super(AppointmentController.class, AppointmentDTO.class);
    }

    @Override
    public AppointmentDTO toModel(Appointment entity) {
        AppointmentDTO dto = instantiateModel(entity);
    dto.add(linkTo(methodOn(AppointmentController.class).get(entity.getId())).withSelfRel());
    dto.setId(entity.getId());
    dto.setPrice(entity.getPrice());
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    dto.setPeriod(entity.getPeriod());
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    dto.setDate(entity.getPeriod().getStartTime().format(dateFormatter));
    dto.setStart(entity.getPeriod().getStartTime().format(timeFormatter));
    dto.setDuration(entity.getPeriod().getDurationMinutes());
    dto.setFree(entity.isFree());
    dto.setPatient(toPatientModel(entity.getPatient()));
    // TODO(Jovan): Add link to controller if needed
    MedicalEmployee employee = entity.getMedicalEmployee();
    dto.setMedicalEmployee(MedicalEmployeeDTO.builder()
        .name(employee.getName())
        .build());
    
    return dto;
    }

    private PatientDTO toPatientModel(Patient patient) {
        if (patient == null) {
            return null;
        }
        return PatientDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .surname(patient.getSurname())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .build()
                .add(linkTo(methodOn(PatientController.class).get(patient.getId())).withSelfRel());
    }
}
