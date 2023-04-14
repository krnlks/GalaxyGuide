package com.guide;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.guide.RomanNumerals.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomanNumeralsTest_Methods {
    @Test
    public void testSplitUpIntoRomanSummands() {
        String[] testLiterals = new String[]{
                "MCMXLIV"
        };

        List<String> summands = RomanNumerals.splitUpIntoRomanSummands(testLiterals[0]);

        List<String> shouldBe = new ArrayList<>();
        shouldBe.add("M");
        shouldBe.add("CM");
        shouldBe.add("XL");
        shouldBe.add("IV");

        assertArrayEquals(shouldBe.toArray(), summands.toArray());
    }

    @Test
    void testGetRomanNumber_int() {
        assertEquals("MCMIII", getRomanNumber(1903));
        assertEquals("MMVI", getRomanNumber(2006));
        assertEquals("MCMXLIV", getRomanNumber(1944));
        assertEquals("MMMCMX", getRomanNumber(3910));
        assertEquals("MMMCMXCIX", getRomanNumber(3999));
    }

    @Test
    void testGetParts() {
        var ex1 = new Integer[] {1000, 900, 3};
        var ex2 = new Integer[] {2000, 6};
        var ex3 = new Integer[] {1000, 900, 40, 4};
        var ex4 = new Integer[] {3000, 900, 10};
        var ex5 = new Integer[] {3000, 900, 90, 9};

        assertArrayEquals(ex1, getParts(1903).toArray());
        assertArrayEquals(ex2, getParts(2006).toArray());
        assertArrayEquals(ex3, getParts(1944).toArray());
        assertArrayEquals(ex4, getParts(3910).toArray());
        assertArrayEquals(ex5, getParts(3999).toArray());
    }

    @Test
    void testGetRomanNumber_parts() {
        var ex1 = Arrays.asList(1000, 900, 3);
        var ex2 = Arrays.asList(2000, 6);
        var ex3 = Arrays.asList(1000, 900, 40, 4);
        var ex4 = Arrays.asList(3000, 900, 10);
        var ex5 = Arrays.asList(3000, 900, 90, 9);

        assertEquals("MCMIII", RomanNumerals.getRomanNumber(ex1));
        assertEquals("MMVI", RomanNumerals.getRomanNumber(ex2));
        assertEquals("MCMXLIV", RomanNumerals.getRomanNumber(ex3));
        assertEquals("MMMCMX", RomanNumerals.getRomanNumber(ex4));
        assertEquals("MMMCMXCIX", RomanNumerals.getRomanNumber(ex5));
    }
}
