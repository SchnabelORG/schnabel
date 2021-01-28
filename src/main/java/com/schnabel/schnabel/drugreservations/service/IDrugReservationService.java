package com.schnabel.schnabel.drugreservations.service;

import java.time.LocalDate;

import com.schnabel.schnabel.drugreservations.model.DrugReservation;
import com.schnabel.schnabel.misc.interfaces.ICrudService;

public interface IDrugReservationService extends ICrudService<DrugReservation, Integer>
{
    Iterable<DrugReservation> getValidForDate(LocalDate date);
}
