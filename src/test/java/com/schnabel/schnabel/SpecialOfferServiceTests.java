package com.schnabel.schnabel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.schnabel.schnabel.pswregistration.model.SpecialOffer;
import com.schnabel.schnabel.pswregistration.repository.ISpecialOfferRepository;
import com.schnabel.schnabel.pswregistration.service.SpecialOfferService;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

public class SpecialOfferServiceTests {

    @ParameterizedTest
    @MethodSource("datePeriods")
    void validPeriodTest(LocalDate from, LocalDate until, boolean isEmpty)
    {
        SpecialOffer so1 = new SpecialOffer(1, "Offer 1", "Aspiring 50% off", LocalDate.of(2020, 8, 1), LocalDate.of(2020, 9, 1));
        SpecialOffer so2 = new SpecialOffer(2, "Offer 2", "Aspiring 50% off", LocalDate.of(2020, 3, 1), LocalDate.of(2020, 9, 1));
        SpecialOffer so3 = new SpecialOffer(3, "Offer 3", "Aspiring 50% off", LocalDate.of(2020, 7, 1), LocalDate.of(2020, 9, 1));
        ArrayList<SpecialOffer> offers = new ArrayList<SpecialOffer>();
        offers.add(so1);
        offers.add(so2);
        offers.add(so3);
        
        ISpecialOfferRepository stubRepository = Mockito.mock(ISpecialOfferRepository.class);
        when(stubRepository.findAll()).thenReturn(offers);
        SpecialOfferService service = new SpecialOfferService(stubRepository);
        ArrayList<SpecialOffer> validOffers = (ArrayList<SpecialOffer>) service.getOffersForTimePeriod(from, until);
        System.out.println(validOffers);
        assertEquals(validOffers.isEmpty(), isEmpty);
    }

    private static Stream<Arguments> datePeriods()
    {
        return Stream.of
        (
            Arguments.of(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 1), false),
            Arguments.of(LocalDate.of(2020, 3, 1), LocalDate.of(2020, 9, 1), false),
            Arguments.of(LocalDate.of(2020, 3, 5), LocalDate.of(2020, 7, 1), false),
            Arguments.of(LocalDate.of(2020, 10, 1), LocalDate.of(2020, 12, 1), true)
        );
    }
}
