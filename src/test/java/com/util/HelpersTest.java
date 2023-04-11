package com.util;

import org.junit.jupiter.api.Test;

import static com.util.Helpers.getMostSignDigit;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpersTest {
    @Test
    void testGetMostSignDigit() {
        assertEquals(9, getMostSignDigit(9));
        assertEquals(9, getMostSignDigit(90));
        assertEquals(9, getMostSignDigit(900));
        assertEquals(3, getMostSignDigit(3000));
    }
}
