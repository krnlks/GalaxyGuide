package com.guide;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RomanNumeralsTest_Requirements {
    @Test
    void addNumeralsExamples() {
        assertEquals(2006, RomanNumerals.getInt("MMVI"));
        assertEquals(1944, RomanNumerals.getInt("MCMXLIV"));
    }

    @Test
    void addValuesWhenSmallSucceedsLarge() {
        assertEquals(6, RomanNumerals.getInt("VI"));
    }

    @Test
    void subtractValuesWhenSmallPrecedesLarge() {
        assertEquals(4, RomanNumerals.getInt("IV"));
    }

    @Test
    void dontRepeatIMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("IIII");
        });
    }

    @Test
    void dontRepeatXMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("XXXX");
        });
    }

    @Test
    void dontRepeatCMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("CCCC");
        });
    }

    @Test
    void dontRepeatMMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("MMMM");
        });
    }

    @Test
    void dontRepeatD() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("DD");
        });
    }

    @Test
    void dontRepeatL() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("LL");
        });
    }

    @Test
    void dontRepeatV() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("VV");
        });
    }

    @Test
    void ICanBeSubtractedFromVAndXOnly() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("IL");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("IC");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("ID");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("IM");
        });

        assertEquals(4, RomanNumerals.getInt("IV"));
        assertEquals(9, RomanNumerals.getInt("IX"));
    }

    @Test
    void XCanBeSubtractedFromLAndCOnly() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("XD");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("XM");
        });

        assertEquals(40, RomanNumerals.getInt("XL"));
        assertEquals(90, RomanNumerals.getInt("XC"));
    }

    @Test
    void CCanBeSubtractedFromDAndMOnly() {
        assertEquals(400, RomanNumerals.getInt("CD"));
        assertEquals(900, RomanNumerals.getInt("CM"));
    }

    @Test
    void doNotSubtractVLD() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("VX");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("LC");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("DM");
        });
    }

    @Test
    void SubtractOnlyOneSmallValueSymbolFromAnyLargeValueSymbol() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("IIV");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("IIX");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("XXL");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("XXC");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("CCD");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("CCM");
        });
    }
}
