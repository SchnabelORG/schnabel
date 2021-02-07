package com.schnabel.schnabel.drugreservations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.schnabel.schnabel.drugreservations.model.DrugReservation;
import com.schnabel.schnabel.drugreservations.repository.IDrugReservationRepository;
import com.schnabel.schnabel.drugreservations.service.DrugReservationService;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.misc.model.Period;
import com.schnabel.schnabel.users.model.Patient;

import org.hibernate.type.TrueFalseType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

public class DrugReservationServiceTests
{
    
    @ParameterizedTest
    @MethodSource("fromDates")
    void validDrugReservationsTest(LocalDate from, boolean isEmpty)
    {
        DrugReservationService service = new DrugReservationService(getStubRepository());
        ArrayList<DrugReservation> validReservations = new ArrayList<DrugReservation>();
        service.getValidForDate(from).forEach(validReservations::add);
        assertEquals(validReservations.isEmpty(), isEmpty);
    }

    private static Stream<Arguments> fromDates()
    {
        return Stream.of
        (
            Arguments.of(LocalDate.of(2020, 01, 01), false),
            Arguments.of(LocalDate.of(2021, 01, 01), false),
            Arguments.of(LocalDate.of(2021, 03, 01), false),
            Arguments.of(LocalDate.of(2021, 03, 02), true)
        );
    }

    private IDrugReservationRepository getStubRepository()
    {
        IDrugReservationRepository stubRepository = Mockito.mock(IDrugReservationRepository.class);
        Address a1 = new Address("21000", "Novi Sad", "Balzakova", 69);
        Address a2 = new Address("11000", "Beograd", "Balzakova", 64);
        Patient p1 = new Patient(1, "Jovan", "Ivosevic", "ivosevic.jovan@uns.ac.rs", a1, true, "ivosevicjovan" );
        Patient p2 = new Patient(2, "Petar", "Petrovic", "petrovic.petar@bg.ac.rs", a2, true, "petrovicpetar" );
        Period p = new Period(LocalDate.of(2020, 7, 1), LocalDate.of(2021, 7, 1));
        Drug d1 = new Drug(1L, "Ciklopentanoperhidrofenantren", "Arnold Schwarzenegger approves!", 1, 100.0, p);
        DrugReservation dr1 = new DrugReservation(1L, p1, d1, LocalDate.of(2021, 02, 03));
        DrugReservation dr2 = new DrugReservation(2L, p2, d1, LocalDate.of(2020, 02, 03));
        DrugReservation dr3 = new DrugReservation(3L, p1, d1, LocalDate.of(2021, 03, 01));
        ArrayList<DrugReservation> reservations = new ArrayList<DrugReservation>();
        reservations.add(dr1);
        reservations.add(dr2);
        reservations.add(dr3);
        when(stubRepository.findAll()).thenReturn(reservations);
        return stubRepository;
    }
}
