package com.guide;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;

public class RomanNumeralTest {
    @Test
    void addNumeralsExample1() {
        assertEquals(RomanNumerals.toInt("MMVI"), 2006);
    }

    @Test
    void addNumeralsExample2() {
        assertEquals(RomanNumerals.toInt("MCMXLIV"), 1944);
    }

    @Test
    void addValuesWhenSmallSucceedsLarge() {
        assertEquals(RomanNumerals.toInt("VI"), 6);
    }

    @Test
    void subtractValuesWhenSmallPrecedesLarge() {
        assertEquals(RomanNumerals.toInt("IV"), 4);
    }

    @Test
    void dontRepeatIMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("IIII");
        });
    }

    @Test
    void dontRepeatXMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("XXXX");
        });
    }

    @Test
    void dontRepeatCMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("CCCC");
        });
    }

    @Test
    void dontRepeatMMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("MMMM");
        });
    }

    @Test
    void dontRepeatD() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("DD");
        });
    }

    @Test
    void dontRepeatL() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("LL");
        });
    }

    @Test
    void dontRepeatV() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("VV");
        });
    }

    

}
