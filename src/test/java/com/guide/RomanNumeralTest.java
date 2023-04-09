package com.guide;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomanNumeralTest {
    @Test
    void addValuesWhenSmallSucceedsLarge() {
        assertEquals(RomanNumerals.toInt("VI"), 6);
    }

    @Test
    void subtractValuesWhenSmallPrecedesLarge() {
        assertEquals(RomanNumerals.toInt("IV"), 4);
    }
}
