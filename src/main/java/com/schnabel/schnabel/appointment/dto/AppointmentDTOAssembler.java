package com.schnabel.schnabel.appointment.dto;
import com.schnabel.schnabel.appointment.controller.AppointmentController;
import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.appointment.model.AppointmentReport;
import com.schnabel.schnabel.users.controller.PatientController;
import com.schnabel.schnabel.users.dto.AllergyDTO;
import com.schnabel.schnabel.users.dto.MedicalEmployeeDTO;
import com.schnabel.schnabel.users.dto.PatientDTO;
import com.schnabel.schnabel.users.model.Allergy;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.model.Patient;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    AppointmentReport appointmentReport = entity.getReport();
    if(appointmentReport == null)
        dto.setIsFinished(false);
    else
        dto.setIsFinished(true);
    // TODO(Jovan): Add link to controller if needed
    MedicalEmployee employee = entity.getMedicalEmployee();
    dto.setMedicalEmployee(MedicalEmployeeDTO.builder()
        .name(employee.getName())
        .surname(employee.getSurname())
        .build());
    dto.setMissed(entity.isMissed());
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
                .allergies(getAllergies(patient.getAllergies()))
                .build()
                .add(linkTo(methodOn(PatientController.class).get(patient.getId())).withSelfRel());
    }
    private List<AllergyDTO> getAllergies(List<Allergy> allergies)
    {
        if(allergies.isEmpty())
            return Collections.emptyList();

        return allergies.stream()
                .map(a -> AllergyDTO.builder()
                        .id(a.getId())
                        .type(a.getAllergyType())
                        .drugId(a.getDrug().getId())
                        .build())
                .collect(Collectors.toList());
    }
}
