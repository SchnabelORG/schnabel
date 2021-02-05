package com.schnabel.schnabel.drugreservations.service;

import java.time.LocalDate;

import com.schnabel.schnabel.drugreservations.model.DrugReservation;
import com.schnabel.schnabel.misc.interfaces.ICrudService;

/**
 * Drug reservation service interface
 */
public interface IDrugReservationService extends ICrudService<DrugReservation, Long>
{
    /**
     * Get drug reservations valid for <b>date</b>
     * @param date for checking whether the reservation's valid
     * 
     * @return Iterable of <b>DrugReservation</b>
     */
    Iterable<DrugReservation> getValidForDate(LocalDate date);
}
