package com.schnabel.schnabel.appointment.controller;

import com.schnabel.schnabel.appointment.dto.RecommendedMedicationDTO;
import com.schnabel.schnabel.appointment.model.AppointmentReport;
import com.schnabel.schnabel.appointment.model.RecommendedMed;
import com.schnabel.schnabel.appointment.service.IAppointmentReportService;
import com.schnabel.schnabel.appointment.service.IRecommendedMedService;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.service.IDrugService;
import com.schnabel.schnabel.security.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * RecommendedMed REST controller
 */
@RestController
@RequestMapping("api/recommendedmed")
public class RecommendedMedController {
    private final IAppointmentReportService reportService;
    private final IRecommendedMedService recommendedMedService;
    private final IDrugService drugService;
    private final JwtUtils jwtUtils;

    @Autowired
    public RecommendedMedController(IAppointmentReportService reportService, IRecommendedMedService recommendedMedServiceService, IDrugService drugService, JwtUtils jwtUtils) {
        this.reportService = reportService;
        this.recommendedMedService = recommendedMedServiceService;
        this.drugService = drugService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/addmed/{appRepId}")
    public ResponseEntity<Boolean> defineAppointment(@PathVariable long appRepId, @RequestBody List<RecommendedMedicationDTO> medications)
    {
        Optional<AppointmentReport> appointmentReport = reportService.get(appRepId);
        if(appointmentReport.isPresent()){
            for (RecommendedMedicationDTO rM:medications) {
                RecommendedMed recMed = new RecommendedMed();
                Optional<Drug> drug = drugService.get(rM.getDrug().getId());
                recMed.setDrug(drug.get());
                recMed.setDurationInDays(rM.getDurationInDays());
                recMed.setQuantity(rM.getQuantity());
                recMed.setTakePerDay(rM.getTakePerDay());
                recMed.setReport(appointmentReport.get());
                recommendedMedService.add(recMed);
            }
            return  ResponseEntity.ok(true);
        }
        return  ResponseEntity.ok(false);
    }
}
