package com.schnabel.schnabel.consultation.service;

import com.schnabel.schnabel.consultation.dto.ConsultationDTO;

/**
 * Consultation service interface
 */
public interface IConsultationService
{
    /**
     * Schedule pharmacological consultation as Patient
     * @param dto DTO containing requried information
     * @return true if scheduled else false
     */
    boolean scheduleConsultation(ConsultationDTO dto);
    /**
     * Cancel consultation if it's more than 24hrs due
     * @param id ID of consultation
     * @return true if cancelled else false
     */
    boolean cancelConsultation(Long id);
}
