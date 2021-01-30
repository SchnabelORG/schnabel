package com.schnabel.schnabel.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import com.schnabel.schnabel.misc.model.Address;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AddressTests
{

    @ParameterizedTest
    @MethodSource("addresses")
    void addressEqualityTest(Address a1, Address a2, boolean isEqual)
    {
        assertEquals(a1.equals(a2), isEqual);
    }

    private static Stream<Arguments> addresses()
    {
        return Stream.of
        (
            Arguments.of(new Address("21000", "Novi Sad", "Balzakova", 69), new Address("21000", "Novi Sad", "Balzakova", 44), false),
            Arguments.of(new Address("2100", "Novi Sad", "Balzakova", 44), new Address("21000", "Novi Sad", "Balzakova", 44), false),
            Arguments.of(new Address("21000", "Novi Sad", "Balzakova", 69), new Address("21000", "Novi Sad", "Balzakova", 69), true),
            Arguments.of(new Address("21000", "Novi Sad", "Balzakova", 69), new Address("21000", "Novi Sad", "Balzakova", 44), false)
        );
    }
}
