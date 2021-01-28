package com.schnabel.schnabel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.schnabel.schnabel.specialoffer.model.SpecialOffer;
import com.schnabel.schnabel.specialoffer.repository.ISpecialOfferRepository;
import com.schnabel.schnabel.specialoffer.service.SpecialOfferService;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

public class SpecialOfferServiceTests {

    @ParameterizedTest
    @MethodSource("datePeriods")
    void validPeriodTest(LocalDate from, LocalDate until, boolean isEmpty)
    {
        SpecialOfferService service = new SpecialOfferService(getStubRepository());

        ArrayList<SpecialOffer> validOffers = (ArrayList<SpecialOffer>) service.getOffersForTimePeriod(from, until);
        assertEquals(validOffers.isEmpty(), isEmpty);
    }

    private ISpecialOfferRepository getStubRepository()
    {
        ISpecialOfferRepository stubRepository = Mockito.mock(ISpecialOfferRepository.class);
        SpecialOffer so1 = new SpecialOffer(1, "Offer 1", "Aspiring 50% off", LocalDate.of(2020, 8, 1), LocalDate.of(2020, 9, 1), "Jankovic");
        SpecialOffer so2 = new SpecialOffer(2, "Offer 2", "Aspiring 50% off", LocalDate.of(2020, 3, 1), LocalDate.of(2020, 9, 1), "Jankovic");
        SpecialOffer so3 = new SpecialOffer(3, "Offer 3", "Aspiring 50% off", LocalDate.of(2020, 7, 1), LocalDate.of(2020, 9, 1), "Jankovic");
        ArrayList<SpecialOffer> offers = new ArrayList<SpecialOffer>();
        offers.add(so1);
        offers.add(so2);
        offers.add(so3);
        when(stubRepository.findAll()).thenReturn(offers);
        return stubRepository;
    }

    private static Stream<Arguments> datePeriods()
    {
        return Stream.of
        (
            Arguments.of(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 1), false),
            Arguments.of(LocalDate.of(2020, 3, 1), LocalDate.of(2020, 9, 1), false),
            Arguments.of(LocalDate.of(2020, 3, 5), LocalDate.of(2020, 7, 1), false),
            Arguments.of(LocalDate.of(2020, 10, 1), LocalDate.of(2020, 12, 1), false),
            Arguments.of(LocalDate.of(2020, 9, 2), LocalDate.of(2020, 12, 1), false)
        );
    }
}
